package ru.practicum.shareit.request.dto;

import lombok.*;
import ru.practicum.shareit.item.dto.ItemByRequestDto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * TODO Sprint add-item-requests.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemRequestDto {
    private Integer id;
    private String description;
    private Integer requester;
    private LocalDateTime created;
    private List<ItemByRequestDto> items;
}
