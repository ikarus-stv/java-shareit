package ru.practicum.shareit.item.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CommentDto {
    private Integer id;
    private String text;
    private Integer item;
    private Integer author;
    private String authorName;
    private LocalDateTime created;
}
