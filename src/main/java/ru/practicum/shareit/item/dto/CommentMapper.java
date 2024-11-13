package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment fromCreateDto(CommentCreateDto commentCreateDto);

    CommentDto toDto(Comment comment);

    List<CommentDto> toListCommentDto(List<Comment> comments);
}
