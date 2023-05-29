package ru.practicum.shareit.item.model;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ItemRepositoryImpl implements ItemRepository{
    private final Map<Integer, List<Item>> items = new HashMap<>();
    private final Map<Integer, Item> itemMap = new HashMap<>();
    private Integer id = 0;

    @Override
    public List<Item> getItemsByUserId(Integer userId) {
        return items.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public Item getItemById(Integer id) {
        if (itemMap.get(id) == null) {
            throw new NotFoundException(String.format("Вещь под номером =%d не найдена", id));
        }
        return itemMap.get(id);
    }

    @Override
    public Item save(Item item) {
        item.setId(++id);
        items.compute(item.getOwnerId(), (userId, userItems) -> {
            if (userItems == null) {
                userItems = new ArrayList<>();
            }
            userItems.add(item);
            return userItems;
        });
        itemMap.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Item item) {
        Item existingItem = itemMap.get(item.getId());
        if (existingItem == null) {
            throw new NotFoundException(String.format("Вещь под номером =%d не найдена", item.getId()));
        }
        if (item.getName() == null) {
            item.setName(existingItem.getName());
        }
        if (item.getDescription() == null) {
            item.setDescription(existingItem.getDescription());
        }
        if (item.getAvailable() == null) {
            item.setAvailable(existingItem.getAvailable());
        }
        items.compute(item.getOwnerId(), (userId, userItems) -> {
            int index = userItems.indexOf(existingItem);
            userItems.set(index, item);
            return userItems;
        });
        itemMap.put(item.getId(), item);
        return item;
    }

    @Override
    public void removeItemByUserIdAndItemId(Integer userId, Integer itemId) {
        if (items.containsKey(userId)) {
            items.get(userId).removeIf(item -> item.getId().equals(itemId));
        }
        itemMap.remove(itemId);
    }

    @Override
    public List<ItemDto> getItemByQuery(String query) {
        return items.values().stream()
                .flatMap(List::stream)
                .filter(item -> item.getAvailable() &&
                        (item.getName().toUpperCase().contains(query.toUpperCase()) ||
                                item.getDescription().toUpperCase().contains(query.toUpperCase())))
                .map(ItemMapper::toDto)
                .collect(Collectors.toList());
    }
}
