package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapperByLibrary {
    ItemMapperByLibrary INSTANCE = Mappers.getMapper(ItemMapperByLibrary.class);

    @Mapping(target = "id", ignore = true)
    Item itemFromCreateDto(ItemCreateDto createDto);

    ItemDto toItemDto(Item item);

    ItemDtoWide toDtoWide(Item item);

    List<ItemByRequestDto> listItem2ListRequestDto(List<Item> itemList);
}
