package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class BookingCreateDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeBookingDto() throws Exception {
        BookingCreateDto bookingCreateDto = new BookingCreateDto();
        bookingCreateDto.setItemId(1);
        bookingCreateDto.setStart(LocalDateTime.of(2026, 11, 19, 11, 11, 11));
        bookingCreateDto.setEnd(LocalDateTime.of(2026, 11, 19, 11, 11, 12));

        String json = objectMapper.writeValueAsString(bookingCreateDto);
        BookingCreateDto deserializedDto = objectMapper.readValue(json, BookingCreateDto.class);

        assertEquals(bookingCreateDto.getItemId(), deserializedDto.getItemId());
        assertEquals(bookingCreateDto.getEnd(), deserializedDto.getEnd());
        assertEquals(bookingCreateDto.getStart(), deserializedDto.getStart());
    }
}
