package ru.practicum.shareit.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class User {
    private Integer id;
    @NotBlank(message = "Имя не может быть пустым")
    private String name;
    @NotBlank
    @Email(message = "электронная почта не может быть пустой и должна содержать символ @")
    private String email;
}
