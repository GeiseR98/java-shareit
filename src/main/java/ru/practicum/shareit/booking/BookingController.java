package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingDto;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService bookingService;
    private static final String USERID = "X-Sharer-User-Id";

    @PostMapping
    public BookingDto save(@RequestHeader(USERID) Integer userId,
                           @RequestBody @Valid BookingDto bookingDto) {
        log.info("Попытка добавить бронирование: {}", bookingDto);
        return bookingService.addNewBooking(userId, bookingDto);
    }

    @GetMapping("/{bookingId}")
    public BookingDto getById(@RequestHeader(USERID) Integer userId,
                              @PathVariable Integer bookingId) {
        log.info("Потытка получить бронирование с ID: {}", bookingId);
        return bookingService.getBookingById(userId, bookingId);
    }

    @PatchMapping("/{bookingId}")
    public BookingDto approveBooking(@RequestHeader(USERID) Integer userId,
                                     @PathVariable Integer bookingId,
                                     @RequestParam boolean approved) {
        log.info(String.valueOf("Попытка подтвердить бронирование с ID {}"), bookingId);
        return bookingService.approveBooking(userId, bookingId, approved);
    }

}
