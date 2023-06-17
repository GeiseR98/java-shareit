package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;
    private static final String USERID = "X-Sharer-User-Id";

    @GetMapping("/{bookingId}")
    public BookingDto getById(@RequestHeader(USERID) Integer userId,
                              @PathVariable Integer bookingId) {
        log.info("Потытка получить бронирование с ID: {}", bookingId);
        return bookingService.getBookingById(userId, bookingId);
    }

}
