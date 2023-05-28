package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.exception.AlreadyExistException;

import javax.validation.Valid;
import java.util.List;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequestMapping(path = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<User> getAll() {
        log.info("Количество пользователей: {}", userService.getAll());
        return userService.getAll();
    }

    @PostMapping
    public User save(@RequestBody @Valid User user) {
        log.info("Попытка добавить пользователя: {}", user);
        try {
            return userService.save(user);
        } catch (AlreadyExistException e) {
            throw new AlreadyExistException("Пользователь с таким email уже существует");
        }
    }

    @PatchMapping("/{userId}")
    public User update(@RequestBody User user, @PathVariable Integer userId) {
        log.info("Попытка обновить пользователя: {}", user);
        return userService.update(userId, user);
    }

    @GetMapping("/{userId}")
    public User getById(@PathVariable Integer userId) {
        log.info("Попытка получить пользователя с id: {}", userId);
        return userService.getById(userId);
    }

    @DeleteMapping("/{userId}")
    public void removeById(@PathVariable Integer userId) {
        log.info("Попытка удаления пользователя с идентификатором: {}", userId);
        userService.removeById(userId);
    }
}
