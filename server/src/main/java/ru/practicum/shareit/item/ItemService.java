package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    Item createItem(Integer userId, ItemCreateDto createDto);

    Item updateItem(Integer userId, Integer itemId, ItemUpdateDto updateDto);

    Collection<Item> getItemsByUser(Integer userId);

    Collection<Item> getItemsByText(String text);

    CommentDto createComment(Integer userId, Integer itemId, CommentCreateDto createDto);

    ItemDtoWide getItemWideById(Integer itemId);
}
