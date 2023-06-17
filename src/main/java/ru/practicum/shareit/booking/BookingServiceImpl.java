package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.utility.Utility;

import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final Utility utility;

    @Transactional
    @Override
    public BookingDto addNewBooking(Integer userId, BookingDto bookingDto) {
        utility.checkCorrectTime(bookingDto.getStart(), bookingDto.getEnd());
        User user = utility.checkUser(userId);
        Item item = utility.checkItem(bookingDto.getItemId());
        utility.checkNotOwner(userId, item);
        if (!item.getAvailable()) {
            throw new ValidationException("Вещь должна быть доступна");
        }
        Booking booking = BookingMapper.toEntity(user, item, bookingDto);
        booking.setStatus(Status.WAITING);
        return BookingMapper.toDto(bookingRepository.save(booking));
    }


    @Override
    public BookingDto getBookingById(Integer userId, Integer bookingId) {
        utility.checkUser(userId);
        Booking booking = utility.checkBooking(bookingId);
        User user = booking.getBooker();
        User owner = booking.getItem().getOwner();
        if (Objects.equals(userId, user.getId()) || Objects.equals(userId, owner.getId())) {
            return BookingMapper.toDto(booking);
        }
        throw new NotFoundException(String.format("Пользователь с ID =%d не является владельцом вещи", userId));
    }


}
