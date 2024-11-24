package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class UserCreateDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeUserCreateDto() throws Exception {
        UserCreateDto userDto = new UserCreateDto();
        userDto.setName("Username");
        userDto.setEmail("mail@mail.com");

        String json = objectMapper.writeValueAsString(userDto);
        UserCreateDto deserializedDto = objectMapper.readValue(json, UserCreateDto.class);

        assertEquals(userDto.getName(), deserializedDto.getName());
        assertEquals(userDto.getEmail(), deserializedDto.getEmail());
    }
}
