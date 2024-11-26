package ru.practicum.shareit.item.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ItemUpdateDto {
    private String name;
    private String description;
    private Boolean available;
}
