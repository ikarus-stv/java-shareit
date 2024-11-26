package ru.practicum.shareit.item.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class CommentCreateDto {
    private String text;
    private Integer item;
    private Integer author;
}