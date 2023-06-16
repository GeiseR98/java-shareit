package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserDto;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        return UserMapper.toDto(userRepository.save(UserMapper.toEntity(userDto)));
    }

    @Override
    @Transactional
    public UserDto update(Integer userId, UserDto userDto) {
        User user = UserMapper.toEntity(userDto);
        user.setId(userId);
        Optional<User> userTemp = userRepository.findById(userId);
        if (user.getName() == null) {
            user.setName(userTemp.get().getName());
        }
        if (user.getEmail() == null) {
            user.setEmail(userTemp.get().getEmail());
        }
        return UserMapper.toDto(userRepository.save(user));
    }

    @Override
    public UserDto getById(Integer id) {
        return UserMapper.toDto(userRepository.findById(id).orElseThrow(() -> new NotFoundException("Пользователя не существует")));
    }

    @Override
    @Transactional
    public void removeById(Integer id) {
        userRepository.deleteById(id);
    }
}
