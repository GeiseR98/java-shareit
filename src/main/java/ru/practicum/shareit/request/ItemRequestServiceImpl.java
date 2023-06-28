package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.model.ItemMapper;
import ru.practicum.shareit.item.model.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.utility.Utility;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemRequestServiceImpl implements ItemRequestService {
    private final Utility utility;
    private final ItemRequestRepository itemRequestRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @Override
    public ItemRequestDto addNewItemRequest(Integer userId, ItemRequestDto itemRequestDto) {
        ItemRequest itemRequest = ItemRequestMapper.toEntity(utility.checkUser(userId), itemRequestDto, null);
        return ItemRequestMapper.toDto(itemRequestRepository.save(itemRequest));
    }

    @Override
    public Collection<ItemRequestDto> getRequests(Integer userId) {
        utility.checkUser(userId);
        List<ItemRequest> itemRequests = itemRequestRepository.findByRequestorId(userId);
        return mapToRequestWithItems(itemRequests);
    }

    private List<ItemRequestDto> mapToRequestWithItems(Iterable<ItemRequest> itemRequests) {
        List<ItemRequestDto> itemRequestWithItems = new ArrayList<>();
        List<Integer> requestIds = new ArrayList<>();

        for (ItemRequest request : itemRequests) {
            requestIds.add(request.getId());
            ItemRequestDto requestDto = ItemRequestMapper.toDto(request);
            itemRequestWithItems.add(requestDto);
        }

        List<Item> items = itemRepository.findByRequestIdIn(requestIds);
        Map<Integer, List<Item>> itemsByRequestId = items.stream()
                .collect(Collectors.groupingBy((Item item) -> item.getRequest().getId()));

        for (ItemRequestDto requestDto : itemRequestWithItems) {
            List<Item> requestItems = itemsByRequestId.getOrDefault(requestDto.getId(), Collections.emptyList());
            requestDto.setItems(ItemMapper.mapToItemDto(requestItems));
        }

        return itemRequestWithItems;
    }
}
