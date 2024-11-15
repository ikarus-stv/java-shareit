package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return ItemMapperByLibrary.INSTANCE.toItemDto(item);
    }

    public static Item itemFromCreateDto(ItemCreateDto createDto) {
        return ItemMapperByLibrary.INSTANCE.itemFromCreateDto(createDto);
    }

    public static void updateFromUpdateDto(Item item, ItemUpdateDto updateDto) {
        if (updateDto.getName() != null) {
           item.setName(updateDto.getName());
        }

        if (updateDto.getDescription() != null) {
            item.setDescription(updateDto.getDescription());
        }

        if (updateDto.getAvailable() != null) {
            item.setAvailable(updateDto.getAvailable());
        }

    }
}
