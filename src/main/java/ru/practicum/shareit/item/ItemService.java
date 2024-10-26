package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

import java.util.Collection;

public interface ItemService {
    Item createItem(Integer userId, ItemCreateDto createDto);

    Item updateItem(Integer userId, Integer itemId, ItemUpdateDto updateDto);

    Item getItemById(Integer itemId);

    Collection<Item> getItemsByUser(Integer userId);

    Collection<Item> getItemsByText(String text);
}
