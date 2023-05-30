package ru.practicum.shareit.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@AllArgsConstructor
public class ItemRequest {
    private Integer id;
    @NotBlank(message = "описание не может быть пустым")
    @Size(min = 1, max = 88, message = "максимальная длина описания — 88 символов")
    private String description;
    @NotNull
    private User requestor;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDateTime created;
}