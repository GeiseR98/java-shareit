package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestDto;

public interface ItemRequestService {
    ItemRequestDto addNewItemRequest(Integer userId, ItemRequestDto itemRequestDto);
}
