package ru.practicum.shareit.booking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.user.dto.UserDto;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
public class BookingControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BookingService bookingService;

    @Autowired
    MockMvc mockMvc;

    private BookingDto createBookingDto(Integer i) {
        BookingDto result = new BookingDto();
        result.setId(i);
        result.setStart(LocalDateTime.now().plusMinutes(i * 5));
        result.setEnd(LocalDateTime.now().plusHours(i * 2));
        result.setItem(new ItemDto());
        result.setBooker(new UserDto());
        result.setStatus(BookingStatus.WAITING);
        return result;
    }

    @Test
    void createBookingTest() throws Exception {
        BookingDto bookingDto = createBookingDto(1);

        when(bookingService.createBooking(any()))
                .thenReturn(bookingDto);

        BookingCreateDto bookingCreateDto = new BookingCreateDto();
        bookingCreateDto.setStart(LocalDateTime.now().plusMinutes(5));
        bookingCreateDto.setEnd(LocalDateTime.now().plusHours(2));
        bookingCreateDto.setItemId(1);


        mockMvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(bookingCreateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(bookingDto.getId()), Integer.class))
                .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.is(bookingDto.getStatus().name())))
                .andExpect(jsonPath("$.start", Matchers.containsString(
                        bookingDto.getStart().toString().substring(0, 24))))
                .andExpect(jsonPath("$.end", Matchers.containsString(
                        bookingDto.getEnd().toString().substring(0, 24))));

        verify(bookingService, times(1))
                .createBooking(any());
    }

    @Test
    void acceptBookingTest() throws Exception {
        BookingDto bookingDto = createBookingDto(1);
        bookingDto.setStatus(BookingStatus.APPROVED);

        when(bookingService.acceptBooking(any(), any(), any()))
                .thenReturn(bookingDto);

        mockMvc.perform(patch("/bookings/" + bookingDto.getId() + "?approved=true")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(bookingDto.getId()), Integer.class))
                .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.is(bookingDto.getStatus().name())))
                .andExpect(jsonPath("$.start", Matchers.containsString(
                        bookingDto.getStart().toString().substring(0, 24))))
                .andExpect(jsonPath("$.end", Matchers.containsString(
                        bookingDto.getEnd().toString().substring(0, 24))));

        verify(bookingService, times(1))
                .acceptBooking(any(), any(), any());
    }



    @Test
    void getBookingByIdTest() throws Exception {
        BookingDto bookingDto = createBookingDto(1);

        when(bookingService.getBookingById(any(), any()))
                .thenReturn(bookingDto);

        mockMvc.perform(get("/bookings/" + bookingDto.getId())
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(bookingDto.getId()), Integer.class))
                .andExpect(jsonPath("$.item", Matchers.notNullValue()))
                .andExpect(jsonPath("$.booker", Matchers.notNullValue()))
                .andExpect(jsonPath("$.status", Matchers.is(bookingDto.getStatus().name())))
                .andExpect(jsonPath("$.start", Matchers.containsString(
                        bookingDto.getStart().toString().substring(0, 24))))
                .andExpect(jsonPath("$.end", Matchers.containsString(
                        bookingDto.getEnd().toString().substring(0, 24))));

        verify(bookingService, times(1))
                .getBookingById(any(), any());
    }

    @Test
    void getBookingsTest() throws Exception {
        List<BookingDto> responseBookingDtoList = List.of(createBookingDto(1), createBookingDto(2));

        when(bookingService.getBookingsOfUser(any(), any()))
                .thenReturn(responseBookingDtoList);

        mockMvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(
                        responseBookingDtoList.get(0).getId()), Integer.class))
                .andExpect(jsonPath("$[1].id", Matchers.is(
                        responseBookingDtoList.get(1).getId()), Integer.class));

        verify(bookingService, times(1))
                .getBookingsOfUser(any(), any());
    }

    @Test
    void getAllByOwnerTest() throws Exception {
        List<BookingDto> responseBookingDtoList = List.of(createBookingDto(1), createBookingDto(2));

        when(bookingService.getBookingsByOwner(any(), any()))
                .thenReturn(responseBookingDtoList);

        mockMvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(responseBookingDtoList.get(0).getId()), Integer.class))
                .andExpect(jsonPath("$[1].id", Matchers.is(responseBookingDtoList.get(1).getId()), Integer.class));

        verify(bookingService, times(1))
                .getBookingsByOwner(any(), any());
    }

}
