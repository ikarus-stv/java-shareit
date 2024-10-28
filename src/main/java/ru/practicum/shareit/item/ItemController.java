package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
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
    public Item getItemById(@PathVariable Integer itemId) {
        return itemService.getItemById(itemId);
    }

    @GetMapping("/search")
    public Collection<Item> getItemsByText(@RequestParam("text") String text) {
        return itemService.getItemsByText(text);
    }


}
