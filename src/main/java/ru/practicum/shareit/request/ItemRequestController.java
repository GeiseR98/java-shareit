package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import javax.validation.Valid;
import java.util.Collection;


@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestService itemRequestService;
    private static final String USERID = "X-Sharer-User_Id";

    @PostMapping
    public ItemRequestDto save(@RequestHeader(USERID) Integer userId,
                               @RequestBody @Valid ItemRequestDto itemRequestDto) {
        log.info("Попытка добавить запрос: {}", itemRequestDto);
        return itemRequestService.addNewItemRequest(userId, itemRequestDto);
    }

    @GetMapping
    public Collection<ItemRequestDto> getRequest(@RequestHeader(USERID) Integer userId) {
        log.info("Попытка получить запросы пользователя: {}", userId);
        return itemRequestService.getRequests(userId);
    }

}
