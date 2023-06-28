package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.utility.Utility;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final Utility utility;
    private final ItemRequestRepository itemRequestRepository;

    @Transactional
    @Override
    public ItemRequestDto addNewItemRequest(Integer userId, ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = ItemRequestMapper.toEntity(utility.checkUser(userId), itemRequestDto, null);
        return ItemRequestMapper.toDto(itemRequestRepository.save(itemRequest));
    }
}
