package ru.practicum.shareit.utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.Booking;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class Utility {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public void checkOwner(Integer userId, Item item) {
        if (!Objects.equals(userId, item.getOwner().getId())) {
            throw new ValidationException("Вы не являетесь владельцем");
        }
    }

    public void checkNotOwner(Integer userId, Item item) {
        if (Objects.equals(userId, item.getOwner().getId())) {
            throw new ValidationException("Вы являетесь владельцем");
        }
    }

    public User checkUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с идентификатором =%d не найден", userId)));
    }

    public Booking checkBooking(Integer bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() ->
                new NotFoundException(String.format("Бронирование с идентификатором =%d не найден", bookingId)));
    }

    public Item checkItem(Integer itemId) {
        return itemRepository.findById(itemId).orElseThrow(() ->
                new NotFoundException(String.format("Вещь с идентификатором =%d не найдена", itemId)));
    }

    public void checkCorrectTime(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new ValidationException("Дата окончания бронирования должна быть после его начала");
        }
    }
}
