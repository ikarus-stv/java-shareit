package ru.practicum.shareit.item.dto;

import ru.practicum.shareit.item.model.Item;

public class ItemMapper {
    public static ItemDto toItemDto(Item item) {
        return ItemMapperByLibrary.INSTANCE.toItemDto(item);
        /*
        Пусть пока будет это комментарий я его в следующем спринте уберу

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
         */
    }

    public static Item itemFromCreateDto(ItemCreateDto createDto) {
        return ItemMapperByLibrary.INSTANCE.itemFromCreateDto(createDto);
        /*
        Пусть пока будет это комментарий я его в следующем спринте уберу

        return Item.builder()
                .name(createDto.getName())
                .description(createDto.getDescription())
                .available(createDto.getAvailable())
                .owner(createDto.getOwner())
                .build();

         */
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
