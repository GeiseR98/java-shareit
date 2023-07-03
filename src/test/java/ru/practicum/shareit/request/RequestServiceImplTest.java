package ru.practicum.shareit.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.model.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.utility.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestServiceImplTest {
    @Mock
    private ItemRequestRepository itemRequestRepository;

    @Mock
    private Utility utility;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ItemRequestServiceImpl itemRequestService;

    @Test
    void addNewItemRequestTest() {
        ItemRequestDto requestDto = new ItemRequestDto();
        User user = new User();
        user.setId(1);
        when(utility.checkUser(1)).thenReturn(user);

        ItemRequest request = ItemRequestMapper.toEntity(user, requestDto, null);
        when(itemRequestRepository.save(any(ItemRequest.class))).thenReturn(request);

        ItemRequestDto actualRequest = itemRequestService.addNewItemRequest(user.getId(), requestDto);

        assertEquals(ItemRequestMapper.toDto(request), actualRequest);
        verify(itemRequestRepository).save(any(ItemRequest.class));
    }

    @Test
    void getRequestsTest() {
        User user = new User();
        user.setId(1);
        when(utility.checkUser(1)).thenReturn(user);

        ItemRequest itemRequest = new ItemRequest();
        List<ItemRequest> itemRequests = new ArrayList<>();
        itemRequests.add(itemRequest);
        List<ItemRequestDto> itemRequestWithItems = itemRequestService.mapToRequestWithItems(itemRequests);
        when(itemRequestRepository.findByRequestorId(1)).thenReturn(itemRequests);

        List<ItemRequestDto> actualRequests = (List<ItemRequestDto>) itemRequestService.getRequests(1);

        assertEquals(itemRequestWithItems, actualRequests);
    }

}
