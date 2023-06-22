package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.*;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.utility.Utility;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final BookingService bookingService;
    private final Utility utility;
    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public ItemDto save(Integer userId, ItemDto itemDto) {
        if (itemDto.getAvailable() == null || !itemDto.getAvailable()) {
            throw new ValidationException("Вещь должна быть доступна");
        }
        User user = utility.checkUser(userId);
        Item item = ItemMapper.toEntity(user, itemDto);
        return ItemMapper.toDto(itemRepository.save(item));
    }

    @Override
    public Collection<ItemDto> getByUserId(Integer userId) {
        utility.checkUser(userId);
        return itemRepository.findByOwnerId(userId).stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(Integer itemId) {
        return ItemMapper.toDto(utility.checkItem(itemId));
    }

    @Override
    @Transactional
    public ItemDto update(Integer userId, ItemDto itemDto, Integer itemId) {
        utility.checkUser(userId);
        Item item = utility.checkItem(itemId);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        itemRepository.save(item);
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getByQuery(String query) {
        if (query.isBlank()) {
            return new ArrayList<>();
        }
        List<Item> userItems = itemRepository.findItemByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);

        return userItems.stream()
                .filter(Item::getAvailable)
                .collect(toList())
                .stream()
                .map(ItemMapper::toDto)
                .collect(toList());
    }

    @Override
    @Transactional
    public void removeById(Integer userId, Integer itemId) {
        utility.checkOwner(userId, utility.checkItem(itemId));
        itemRepository.deleteByOwnerIdAndId(userId, itemId);
    }

    @Override
    public ItemWithBooking getItemById(Integer userId, Integer itemId) {
        Item item = utility.checkItem(itemId);

        BookingDto lastBooking = null;
        BookingDto nextBooking = null;
        if (Objects.equals(userId, item.getOwner().getId())) {
            LocalDateTime currentDateTime = LocalDateTime.now();

            Optional<Booking> lastBookingOptional = bookingService
                    .findFirstByItemIdAndStartBeforeAndStatusOrderByStartDesc(item.getId(), currentDateTime, Status.APPROVED);
            Optional<Booking> nextBookingOptional = bookingService
                    .findFirstByItemIdAndEndAfterAndStatusOrderByStartAsc(item.getId(), currentDateTime, Status.APPROVED);

            if (lastBookingOptional.isPresent()) {
                lastBooking = BookingMapper.toDto(lastBookingOptional.get());
            }
            if (nextBookingOptional.isPresent()) {
                Booking nextBookingForDto = nextBookingOptional.get();
                if (lastBookingOptional.isEmpty() || !lastBookingOptional.get().equals(nextBookingForDto)) {
                    nextBooking = BookingMapper.toDto(nextBookingForDto);
                }
            }
        }
        List<CommentDto> comments = CommentMapper.toDtoList(commentRepository.findAllByItemId(itemId));
        return ItemMapper.toEntityWithBooking(item, lastBooking, nextBooking, comments);
    }

    @Override
    public Collection<ItemWithBooking> getItemsByUserId(Integer userId) {
        utility.checkUser(userId);
        return mapToItemWithBooking(itemRepository.findByOwnerId(userId));
    }

    @Override
    public CommentDto addNewComment(Integer userId, CommentDto commentDto, Integer itemId) {
        List<Booking> bookings = bookingService.findBookingByUserIdAndFinishAfterNow(userId);
        boolean userIsBooker = bookings.stream()
                .anyMatch(booking -> Objects.equals(booking.getItem().getId(), itemId));

        if (!userIsBooker) {
            throw new ValidationException("Пользователь не брал в аренду вещь");
        }

        User user = utility.checkUser(userId);
        Item item = utility.checkItem(itemId);

        Comment comment = CommentMapper.toEntity(user, item, commentDto);

        comment.setCreated(LocalDateTime.now());

        return CommentMapper.toDto(commentRepository.save(comment));
    }

    private List<ItemWithBooking> mapToItemWithBooking(Iterable<Item> items) {
        List<ItemWithBooking> itemWithBookings = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        Map<Item, List<Comment>> comments = commentRepository.findByItemIn(items,  Sort.by(Sort.Direction.DESC, "created"))
                .stream()
                .collect(Collectors.groupingBy(Comment::getItem));

        Map<Item, List<Booking>> bookingsMap = bookingService.findByItemIn(items)
                .stream()
                .collect(Collectors.groupingBy(Booking::getItem));

        for (Item item : items) {
            BookingDto lastBooking = getLastBookingDtoForItem(item, now, bookingsMap.getOrDefault(item, Collections.emptyList()));
            BookingDto nextBooking = getNextBookingDtoForItem(item, now, bookingsMap.getOrDefault(item, Collections.emptyList()));
            List<CommentDto> itemComments = CommentMapper.toDtoList(comments.getOrDefault(item, Collections.emptyList()));
            itemWithBookings.add(ItemMapper.toEntityWithBooking(item, lastBooking, nextBooking, itemComments));

        }

        return itemWithBookings;
    }

    private BookingDto getLastBookingDtoForItem(Item item, LocalDateTime now, List<Booking> bookings) {
        return bookings.stream()
                .filter(booking -> booking.getStart().isBefore(now))
                .max(Comparator.comparing(Booking::getStart))
                .map(BookingMapper::toDto)
                .orElse(null);
    }

    private BookingDto getNextBookingDtoForItem(Item item, LocalDateTime now, List<Booking> bookings) {
        return bookings.stream()
                .filter(booking -> booking.getStart().isAfter(now))
                .min(Comparator.comparing(Booking::getStart))
                .map(BookingMapper::toDto)
                .orElse(null);
    }




}
