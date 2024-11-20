package ru.practicum.shareit.item.dto;

import lombok.Data;

@Data
public class CommentCreateDto {
    private String text;
    private Integer item;
    private Integer author;
}