package ru.practicum.shareit.item.dto;

import lombok.*;

/**
 * TODO Sprint add-controllers.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Integer id;
    private String name;
    private String description;
    private Boolean available;
    private Integer owner;
    private Integer request;
}
