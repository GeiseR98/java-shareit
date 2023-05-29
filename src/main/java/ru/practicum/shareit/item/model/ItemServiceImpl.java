package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.UserRepository;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public Item save(Integer userId, ItemDto itemDto) {
        userRepository.getById(userId);
        Item item = ItemMapper.toItem(userId, itemDto);
        if (item.getAvailable() == null || !item.getAvailable()) {
            throw new ValidationException("Вещь должна быть доступна");
        }
        return itemRepository.save(item);
    }

    @Override
    public Collection<Item> getByUserId(Integer userId) {
        userRepository.getById(userId);
        return itemRepository.getItemsByUserId(userId);
    }

    @Override
    public ItemDto getById(Integer itemId) {
        return ItemMapper.toDto(itemRepository.getItemById(itemId));
    }

    @Override
    public ItemDto update(Integer userId, ItemDto itemDto, Integer itemId) {
        userRepository.getById(userId);
        itemDto.setId(itemId);
        validate(userId, itemRepository.getItemById(itemDto.getId()));
        return ItemMapper.toDto(itemRepository.update(ItemMapper.toItem(userId, itemDto)));
    }

    @Override
    public List<ItemDto> getByQuery(String query) {
        if (query.isBlank()) {
            return new ArrayList<>();
        }
        return itemRepository.getItemByQuery(query);
    }

    @Override
    public void removeById(Integer userId, Integer itemId) {
        userRepository.getById(userId);
        validate(userId, itemRepository.getItemById(itemId));
        itemRepository.removeItemByUserIdAndItemId(userId, itemId);
    }

    private void validate(Integer userId, Item item) {
        if (!Objects.equals(userId, item.getOwnerId())) {
            throw new NotFoundException("Вы не являетесь владельцем");
        }
    }
}
