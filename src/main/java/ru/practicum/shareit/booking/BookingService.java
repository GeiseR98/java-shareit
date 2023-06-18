package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

public interface BookingService {
    BookingDto getBookingById(Integer userId, Integer bookingId);

    BookingDto addNewBooking(Integer userId, BookingDto bookingDto);

    BookingDto approveBooking(Integer userId, Integer bookingId, boolean approve);

    List<BookingDto> getByUserId(Integer userId, String state);
}
