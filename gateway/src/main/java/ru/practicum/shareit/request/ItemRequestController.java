package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.ItemRequestClient;

/**
 * TODO Sprint add-item-requests.
 */
@Controller
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
@Slf4j
@Validated

public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    ResponseEntity<Object> createRequest(@RequestHeader("X-Sharer-User-Id") Integer userId, @RequestBody CreateItemRequestDto createItemRequestDto) {
        log.info("Creating request userId={} request={}", userId, createItemRequestDto);
        createItemRequestDto.setRequester(userId);
        return itemRequestClient.createRequest(userId, createItemRequestDto);
    }

    @GetMapping
    ResponseEntity<Object> getUserRequests(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("getUserRequests userId={}", userId);
        return itemRequestClient.getUserRequests(userId);
    }

    @GetMapping("/all")
    ResponseEntity<Object> getRequestsAll() {
        log.info("getRequestsAll");
        return itemRequestClient.getAllRequests();
    }

    @GetMapping("/{requestId}")
    ResponseEntity<Object> getRequestById(@PathVariable("requestId") Integer requestId) {
        log.info("getRequestById Id={}", requestId);
        return itemRequestClient.getRequestById(requestId);
    }

}
