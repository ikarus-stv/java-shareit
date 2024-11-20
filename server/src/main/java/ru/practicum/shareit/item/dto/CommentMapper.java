package ru.practicum.shareit.item.dto;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.practicum.shareit.item.model.Comment;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CommentMapper {
    Comment fromCreateDto(CommentCreateDto commentCreateDto);

    CommentDto toDto(Comment comment);

    List<CommentDto> toListCommentDto(List<Comment> comments);
}
