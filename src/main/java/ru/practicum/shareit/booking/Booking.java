package ru.practicum.shareit.booking;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Booking {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDateTime start;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDateTime end;
    @NotNull
    private Item item;
    @NotNull
    private User booker;
    @NotNull
    private Status status;
}