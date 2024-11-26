package ru.practicum.shareit.booking.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class BookingDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeBookingDto() throws Exception {
        UserDto userDto = new UserDto(1, "Username1", "mail1@mail.com");
        ItemDto itemToSet = new ItemDto();
        itemToSet.setId(1);
        itemToSet.setName("Itemname");
        itemToSet.setDescription("Description.");
        itemToSet.setAvailable(true);
        itemToSet.setOwner(2);
        itemToSet.setRequest(1);

        UserDto bookerToSet = new UserDto();
        bookerToSet.setId(2);
        bookerToSet.setName("Username2");
        bookerToSet.setEmail("mail2@mail.com");

        BookingDto bookingDto = new BookingDto();
        bookingDto.setId(1);
        bookingDto.setStart(LocalDateTime.of(2025, 11, 18, 11, 11, 11));
        bookingDto.setEnd(LocalDateTime.of(2025, 11, 18, 11, 11, 12));
        bookingDto.setItem(itemToSet);
        bookingDto.setBooker(bookerToSet);
        bookingDto.setStatus(BookingStatus.WAITING);

        String json = objectMapper.writeValueAsString(bookingDto);
        BookingDto deserializedDto = objectMapper.readValue(json, BookingDto.class);

        assertEquals(bookingDto.getId(), deserializedDto.getId());
        assertEquals(bookingDto.getEnd(), deserializedDto.getEnd());
        assertEquals(bookingDto.getStart(), deserializedDto.getStart());

        assertEquals(bookingDto.getItem().getId(), deserializedDto.getItem().getId());
        assertEquals(bookingDto.getItem().getName(), deserializedDto.getItem().getName());
        assertEquals(bookingDto.getItem().getDescription(), deserializedDto.getItem().getDescription());

        assertEquals(bookingDto.getBooker().getId(), deserializedDto.getBooker().getId());
        assertEquals(bookingDto.getBooker().getName(), deserializedDto.getBooker().getName());
        assertEquals(bookingDto.getBooker().getEmail(), deserializedDto.getBooker().getEmail());

        assertEquals(bookingDto.getStatus(), deserializedDto.getStatus());
    }
}
