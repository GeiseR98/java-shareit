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

/*
PASS
Status code is 200
PASS
Has booking patch response
PASS
Test booking 'id' field
FAIL
Test booking 'start' field | AssertionError: "start" must be "undefined": expected '2023-06-20T15:34:12' to deeply equal undefined
FAIL
Test booking 'end' field | AssertionError: "end" must be "undefined": expected '2023-06-20T15:34:13' to deeply equal undefined
PASS
Test booking 'status' field
PASS
Test booking 'booker.id' field
PASS
Test booking 'item.id' field
PASS
Test booking 'item.name' field
*/
    /*
    pm.test("Test booking 'start' field", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('start');
    pm.expect(jsonData.start, '"start"  must be "' + pm.environment.get('start') + '"').to.eql(pm.environment.get('start'));
});
pm.test("Test booking 'end' field", function () {
    var jsonData = pm.response.json();
    pm.expect(jsonData).to.have.property('end');
    pm.expect(jsonData.end, '"end"  must be "' + pm.environment.get('end') + '"').to.eql(pm.environment.get('end'));
});
     */
}