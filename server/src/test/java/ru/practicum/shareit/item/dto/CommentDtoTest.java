package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class CommentDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeCommentDto() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(1);
        commentDto.setText("text");
        commentDto.setItem(2);
        commentDto.setAuthor(2);
        commentDto.setAuthorName("aname");

        String json = objectMapper.writeValueAsString(commentDto);
        CommentDto deserializedDto = objectMapper.readValue(json, CommentDto.class);

        assertEquals(commentDto.getId(), deserializedDto.getId());
        assertEquals(commentDto.getText(), deserializedDto.getText());
        assertEquals(commentDto.getItem(), deserializedDto.getItem());
        assertEquals(commentDto.getAuthor(), deserializedDto.getAuthor());
        assertEquals(commentDto.getAuthorName(), deserializedDto.getAuthorName());
    }

    @Test
    public void testSerializeDeserializeCommentCreateDto() throws Exception {
        CommentCreateDto commentCreateDto = new CommentCreateDto();
        commentCreateDto.setText("text");
        commentCreateDto.setItem(2);
        commentCreateDto.setAuthor(2);

        String json = objectMapper.writeValueAsString(commentCreateDto);
        CommentDto deserializedDto = objectMapper.readValue(json, CommentDto.class);

        assertEquals(commentCreateDto.getText(), deserializedDto.getText());
        assertEquals(commentCreateDto.getItem(), deserializedDto.getItem());
        assertEquals(commentCreateDto.getAuthor(), deserializedDto.getAuthor());
    }

}
