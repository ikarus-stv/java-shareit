package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

/**
 * TODO Sprint add-controllers.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public Item createItem(@RequestHeader("X-Sharer-User-Id") Integer userId, @Valid @RequestBody ItemCreateDto createDto) {
        Item item = itemService.createItem(userId, createDto);
        return item;
    }

    @PatchMapping("/{itemId}")
    public Item updateItem(@RequestHeader("X-Sharer-User-Id") Integer userId, @PathVariable Integer itemId, @Valid @RequestBody ItemUpdateDto updateDto) {
        Item item = itemService.updateItem(userId, itemId, updateDto);
        return item;
    }

    @GetMapping
    public Collection<Item> getItemsByUser(@RequestHeader("X-Sharer-User-Id") Integer userId) {
        return itemService.getItemsByUser(userId);
    }

    @GetMapping("/{itemId}")
    public ItemDtoWide getItemById(@PathVariable Integer itemId) {
        // return ItemMapperByLibrary.INSTANCE.toItemDto(itemService.getItemById(itemId));
        return itemService.getItemWideById(itemId);
    }

    @GetMapping("/search")
    public Collection<Item> getItemsByText(@RequestParam("text") String text) {
        return itemService.getItemsByText(text);
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestHeader("X-Sharer-User-Id") Integer userId, @PathVariable Integer itemId, @Valid @RequestBody CommentCreateDto createDto) {
        CommentDto commentDto = itemService.createComment(userId, itemId, createDto);
        return commentDto;
    }

}
