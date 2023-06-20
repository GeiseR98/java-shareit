package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Status;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class BookingDto {
    private Integer id;
    @NotNull
    @Future
    private LocalDateTime start;
    @NotNull
    @Future
    private LocalDateTime end;
    private Integer itemId;
    private Integer bookerId;
    private Status status;

/*
PASS
Status code is 200 or 201
PASS
Has booking create response
PASS
Test booking 'id' field
PASS
Test booking 'start' field
PASS
Test booking 'end' field
FAIL
Test booking 'status' field | AssertionError: expected { id: 1, …(4) } to have property 'status'
FAIL
Test booking 'booker.id' field | AssertionError: expected { id: 1, …(4) } to have property 'booker'
FAIL
Test booking 'item.id' field | AssertionError: expected { id: 1, …(4) } to have property 'item'
FAIL
Test booking 'item.name' field | AssertionError: expected { id: 1, …(4) } to have property 'item'
*/
}