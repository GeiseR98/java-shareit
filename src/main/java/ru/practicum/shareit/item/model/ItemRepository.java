package ru.practicum.shareit.item.model;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemRepository {
    List<Item> getItemsByUserId(Integer userId);

    Item getItemById(Integer id);

    Item save(Item item);

    Item update(Item item);

    void removeItemByUserIdAndItemId(Integer userId, Integer itemId);

    List<ItemDto> getItemByQuery(String query);
}
