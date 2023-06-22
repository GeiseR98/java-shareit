package ru.practicum.shareit.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.booking.Status;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

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
    private User booker;
    private Item item;


}
/*

Test booking[0] 'id' field | AssertionError: "id" must be 5: expected 8 to deeply equal 5
Test booking[0] 'start' field | AssertionError: "start" must be "undefined": expected '2023-06-22T17:43:00' to deeply equal undefined
Test booking[0] 'end' field | AssertionError: "end" must be "undefined": expected '2023-06-22T18:42:58' to deeply equal undefined
Test booking[0] 'status' field | AssertionError: "status" must be "REJECTED": expected 'APPROVED' to deeply equal 'REJECTED'
Test booking[0] 'item.id' field | AssertionError: "item.id" must be "3": expected 4 to deeply equal 3
Test booking[0] 'item.name' field | AssertionError: "item.name" must be "Клей Момент": expected 'Кухонный стол' to deeply equal 'Клей Момент'
Test booking[1] 'id' field | AssertionError: "id" must be 8: expected 5 to deeply equal 8
Test booking[1] 'start' field | AssertionError: "start" must be "2023-06-22T17:43:00": expected '2023-06-22T17:42:53' to deeply equal '2023-06-22T17:43:00'
Test booking[0] 'end' field | AssertionError: "end" must be "2023-06-22T18:42:58": expected '2023-06-23T17:42:50' to deeply equal '2023-06-22T18:42:58'
Test booking[1] 'status' field | AssertionError: "status" must be "APPROVED": expected 'REJECTED' to deeply equal 'APPROVED'
Test booking[1] 'item.id' field | AssertionError: "item.id" must be "4": expected 3 to deeply equal 4
Test booking[1] 'item.name' field | AssertionError: "item.name" must be "Кухонный стол": expected 'Клей Момент' to deeply equal 'Кухонный стол'
Test booking[0] 'start' field | AssertionError: "start" must be "undefined": expected '2023-06-22T17:42:53' to deeply equal undefined
Test booking[0] 'end' field | AssertionError: "end" must be "undefined": expected '2023-06-23T17:42:50' to deeply equal undefined
Test booking[0] 'start' field | AssertionError: "start" must be "undefined": expected '2023-06-22T17:42:53' to deeply equal undefined
Test booking[0] 'end' field | AssertionError: "end" must be "undefined": expected '2023-06-22T17:42:54' to deeply equal undefined
Test booking[1] 'start' field | AssertionError: "start" must be "undefined": expected '2023-06-22T17:42:48' to deeply equal undefined
Test booking[0] 'end' field | AssertionError: "end" must be "undefined": expected '2023-06-22T17:42:49' to deeply equal undefined
Test booking[0] 'start' field | AssertionError: "start" must be "undefined": expected '2023-06-22T17:42:53' to deeply equal undefined
Test booking[0] 'end' field | AssertionError: "end" must be "undefined": expected '2023-06-22T17:42:54' to deeply equal undefined
Test booking[1] 'start' field | AssertionError: "start" must be "undefined": expected '2023-06-22T17:42:48' to deeply equal undefined
Test booking[0] 'end' field | AssertionError: "end" must be "undefined": expected '2023-06-22T17:42:49' to deeply equal undefined

 */