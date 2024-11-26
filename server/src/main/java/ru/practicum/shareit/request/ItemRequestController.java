package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService service;

    @PostMapping
    public ItemRequestDto createRequest(@RequestHeader("X-Sharer-User-Id") Integer userId, @RequestBody CreateItemRequestDto createItemRequestDto) {
        createItemRequestDto.setRequester(userId);
        return service.createRequest(createItemRequestDto);
    }

    @GetMapping
    public List<ItemRequestDto> getUserRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return service.getUserRequests(userId);
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getRequestsAll() {
        return service.getAllRequests();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getRequestById(@PathVariable("requestId") Integer requestId) {
        return service.getRequestById(requestId);
    }

}
