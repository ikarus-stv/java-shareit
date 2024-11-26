package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

@Controller
@RequestMapping(path = "/items")
@RequiredArgsConstructor
@Slf4j
@Validated

public class ItemController {

    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                             @Valid @RequestBody ItemCreateDto createDto) {
        log.info("Creating item userId={} item={}", userId, createDto);
        return itemClient.createItem(userId, createDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                             @PathVariable Integer itemId,
                                             @Valid @RequestBody ItemUpdateDto updateDto) {
        log.info("Updating item userId={} itemId={} item={}", userId, itemId, updateDto);
        return itemClient.updateItem(userId, itemId, updateDto);
    }

    @GetMapping
    public ResponseEntity<Object> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        log.info("Get items by userId={}", userId);
        return itemClient.getItemsByUser(userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable Integer itemId) {
        log.info("Get items by itemId={}", itemId);
        return itemClient.getItemWideById(itemId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemsByText(@RequestParam("text") String text) {
        log.info("Search by Text={}", text);
        return itemClient.getItemsByText(text);
    }


    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                                @PathVariable Integer itemId,
                                                @Valid @RequestBody CommentCreateDto createDto) {
        log.info("Create comment userid={}, itemid={}, comment={}", userId, itemId, createDto);
        return itemClient.createComment(userId, itemId, createDto);
    }

}
