package ru.practicum.shareit.item;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.item.model.*;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.utility.Utility;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private Utility utility;

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    public void getItems_ValidUserId_ReturnsItemWithBookingList() {
        Integer userId = 1;
        Integer from = 0;
        Integer size = 10;

        List<Item> items = Arrays.asList(new Item(), new Item());
        when(utility.checkUser(userId)).thenReturn((new User()));
        when(itemRepository.findByOwnerId(userId, PageRequest.of(0, 10, Sort.Direction.ASC, "id")))
                .thenReturn(new PageImpl<>(items));

        Collection<ItemWithBooking> result = itemService.getItemsByUserId(userId, from, size);

        assertEquals(items.size(), result.size());

    }
}
