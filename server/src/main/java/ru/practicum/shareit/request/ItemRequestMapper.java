package ru.practicum.shareit.request;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ItemRequestMapper {
    public abstract ItemRequest fromCreateDto(CreateItemRequestDto createItemRequestDto);

    public abstract ItemRequestDto itemRequest2Dto(ItemRequest itemRequest);

    public abstract List<ItemRequestDto> makeListOfDto(List<ItemRequest> itemRequestList);
}
