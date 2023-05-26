package ru.practicum.shareit.user;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.AlreadyExistException;
import ru.practicum.shareit.exception.NotFoundException;

import java.util.*;

@Component
public class UserRepositoryImpl implements UserRepository{
    private final Map<Integer, User> users = new HashMap<>();
    private final Set<String> userEmails = new HashSet<>();
    private int id = 0;

    @Override
    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User save(User user) {
        if (userEmails.contains(user.getEmail())) {
            throw new AlreadyExistException("Пользователь с таким email уже существует");
        }
        user.setId(id++);
        users.put(user.getId(), user);
        userEmails.add(user.getEmail());
        return user;
    }

    @Override
    public User update(User user) {
        if (!users.containsKey(user.getId())) {
            throw new NotFoundException("Пользователя не существует");
        }

        for (User userCheck : users.values()) {
            if (userCheck.getEmail().equals(user.getEmail()) && !user.getId().equals(userCheck.getId())) {
                throw new AlreadyExistException("Пользователь с таким email уже существует");
            }
        }

        for (User userTemp : users.values()) {
            if (userTemp.getId().equals(user.getId())) {
                if (user.getName() == null) {
                    user.setName(userTemp.getName());
                }
                if (user.getEmail() == null) {
                    user.setEmail(userTemp.getEmail());
                }
                users.replace(user.getId(), user);
                userEmails.remove(userTemp.getEmail());
                userEmails.add(user.getEmail());
            }
        }
        return user;
    }

    @Override
    public User getById(Integer id) {
        User user = users.get(id);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }
        return user;
    }

    @Override
    public void removeById(Integer id) {
        User user = users.remove(id);
        if (user == null) {
            throw new NotFoundException("Пользователя не существует");
        }
        userEmails.remove(user.getEmail());
    }
}
