package ru.practicum.shareit.item.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemByRequestDto {
    private Integer id;
    private String name;
    private Integer owner;
}
