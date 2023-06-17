package ru.practicum.shareit.item.model;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.utility.Utility;

import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final Utility utility;

    @Override
    @Transactional
    public ItemDto save(Integer userId, ItemDto itemDto) {
        if (itemDto.getAvailable() == null || !itemDto.getAvailable()) {
            throw new ValidationException("Вещь должна быть доступна");
        }
        User user = utility.checkUser(userId);
        Item item = ItemMapper.toEntity(user, itemDto);
        return ItemMapper.toDto(itemRepository.save(item));
    }

    @Override
    public Collection<ItemDto> getByUserId(Integer userId) {
        utility.checkUser(userId);
        return itemRepository.findByOwnerId(userId).stream()
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ItemDto getById(Integer itemId) {
        return ItemMapper.toDto(utility.checkItem(itemId));
    }

    @Override
    @Transactional
    public ItemDto update(Integer userId, ItemDto itemDto, Integer itemId) {
        utility.checkUser(userId);
        Item item = utility.checkItem(itemId);
        if (itemDto.getName() != null) {
            item.setName(itemDto.getName());
        }
        if (itemDto.getDescription() != null) {
            item.setDescription(itemDto.getDescription());
        }
        if (itemDto.getAvailable() != null) {
            item.setAvailable(itemDto.getAvailable());
        }
        itemRepository.save(item);
        return ItemMapper.toDto(item);
    }

    @Override
    public List<ItemDto> getByQuery(String query) {
        if (query.isBlank()) {
            return new ArrayList<>();
        }
        List<Item> userItems = itemRepository.findItemByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(query, query);

        return userItems.stream()
                .filter(Item::getAvailable)
                .collect(toList())
                .stream()
                .map(ItemMapper::toDto)
                .collect(toList());
    }

    @Override
    @Transactional
    public void removeById(Integer userId, Integer itemId) {
        utility.checkOwner(userId, utility.checkItem(itemId));
        itemRepository.deleteByOwnerIdAndId(userId, itemId);
    }
}
