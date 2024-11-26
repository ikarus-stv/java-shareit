package ru.practicum.shareit.request.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CreateItemRequestDto {
    private String description;
    private Integer requester;
}
