package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.Collection;

public interface ItemRequestService {
    ItemRequestDto addNewItemRequest(Integer userId, ItemRequestDto itemRequestDto);

    Collection<ItemRequestDto> getRequests(Integer userId);
}
