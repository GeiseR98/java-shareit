package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * TODO Sprint add-controllers.
 */
@Data
@AllArgsConstructor
public class ItemDto {
    private Integer id;
    @NotBlank(message = "название не может быть пустым")
    @Size(min = 1, max = 50, message = "максимальная длина длина — 50 символов")
    private String name;
    @NotBlank(message = "описание не может быть пустым")
    @Size(min = 1, max = 88, message = "максимальная длина описания — 88 символов")
    private String description;
    @NotNull
    private Boolean available;
    private Integer requestId;
}
