package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.model.Item;

@Mapper
public interface ItemMapperByLibrary {
    ItemMapperByLibrary INSTANCE = Mappers.getMapper(ItemMapperByLibrary.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "request", ignore = true)
    Item itemFromCreateDto(ItemCreateDto createDto);

    ItemDto toItemDto(Item item);

}
