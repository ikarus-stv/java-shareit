package ru.practicum.shareit.item.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.practicum.shareit.item.dto.CommentCreateDto;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.CommentMapper;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {
    private CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    private User author;
    private User owner;
    private Item item;

    @Test
    void toCommentFromCommentCreateDtoTest() {
        CommentCreateDto commentCreateDto  = new CommentCreateDto();
        commentCreateDto.setItem(1);
        commentCreateDto.setAuthor(2);
        commentCreateDto.setText("text commenta");

        Comment comment = commentMapper.fromCreateDto(commentCreateDto);

        assertEquals(commentCreateDto.getItem(), comment.getItem());
        assertEquals(commentCreateDto.getAuthor(), comment.getAuthor());
        assertEquals(commentCreateDto.getText(), comment.getText());
    }

    @Test
    void toDtoFromCommentTest() {
        Comment comment = new Comment();
        comment.setId(1);
        comment.setAuthor(1);
        comment.setText("text");
        comment.setItem(2);

        CommentDto commentDto = commentMapper.toDto(comment);

        assertEquals(commentDto.getId(), comment.getId());
        assertEquals(commentDto.getText(), comment.getText());
        assertEquals(commentDto.getAuthor(), comment.getAuthor());
        assertEquals(commentDto.getItem(), comment.getItem());
    }
}
