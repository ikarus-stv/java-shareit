package ru.practicum.shareit.user.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class UserDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeReturnUserDto() throws Exception {
        UserDto returnUserDto = new UserDto();
        returnUserDto.setId(1);
        returnUserDto.setName("Username");
        returnUserDto.setEmail("mail@mail.com");

        String json = objectMapper.writeValueAsString(returnUserDto);
        UserDto deserializedDto = objectMapper.readValue(json, UserDto.class);

        assertEquals(returnUserDto.getId(), deserializedDto.getId());
        assertEquals(returnUserDto.getName(), deserializedDto.getName());
        assertEquals(returnUserDto.getEmail(), deserializedDto.getEmail());
    }
}
