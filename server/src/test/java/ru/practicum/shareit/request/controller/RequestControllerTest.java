package ru.practicum.shareit.request.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.ItemRequestService;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
public class RequestControllerTest {

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ItemRequestService itemRequestService;

    @Autowired
    MockMvc mockMvc;

    private ItemRequestDto getItemRequestDto(Integer i) {
        return ItemRequestDto.builder()
                .id(i)
                .description("Description " + i)
                .requester(1)
                .created(LocalDateTime.now())
                .items(new ArrayList<>()).build();
    }

    @Test
    void createItemRequestTest() throws Exception {
        ItemRequestDto itemRequestDto = getItemRequestDto(1);

        when(itemRequestService.createRequest(any()))
                .thenReturn(itemRequestDto);

        mockMvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new ItemRequestDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemRequestDto.getId()), Integer.class))
                .andExpect(jsonPath("$.description", Matchers.is(itemRequestDto.getDescription())))
                .andExpect(jsonPath("$.requester", Matchers.notNullValue()))
                .andExpect(jsonPath("$.created", Matchers.containsString(
                        itemRequestDto.getCreated().toString().substring(0, 24))))
                .andExpect(jsonPath("$.items", Matchers.notNullValue()));

        verify(itemRequestService, times(1))
                .createRequest(any());
    }
}
