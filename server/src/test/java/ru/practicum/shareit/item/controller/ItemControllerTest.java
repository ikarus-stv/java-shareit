package ru.practicum.shareit.item.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.ItemDtoWide;
import ru.practicum.shareit.item.model.Item;


import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
public class ItemControllerTest {

    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    ItemService itemService;
    @Autowired
    MockMvc mockMvc;


    private Item testItem(Integer i) {
        Item result = new Item();
        result.setId(i);
        result.setName("User" + i);
        result.setDescription("Description" + i);
        result.setAvailable(true);
        result.setOwner(1);
        result.setRequestId(5);
        return result;
    }


    @Test
    void createItemTest() throws Exception {
        Item item = testItem(1);

        ItemCreateDto itemCreateDto = new ItemCreateDto();
        itemCreateDto.setName("1");
        itemCreateDto.setDescription("2");
        itemCreateDto.setAvailable(true);

        when(itemService.createItem(any(), any()))
                .thenReturn(item);

        mockMvc.perform(
                        post("/items")
                                .header("X-Sharer-User-Id", 1)
                                .content(objectMapper.writeValueAsString(itemCreateDto))
                                .characterEncoding(StandardCharsets.UTF_8)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(item.getId()), Integer.class))
                .andExpect(jsonPath("$.name", Matchers.is(item.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(item.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(item.getAvailable())))
                .andExpect(jsonPath("$.owner", Matchers.is(item.getOwner()), Integer.class))
                .andExpect(jsonPath("$.requestId", Matchers.is(item.getRequestId()), Integer.class));

        verify(itemService, times(1))
                .createItem(any(), any());



        ;

        /*

        mockMvc.perform(post("/items")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new ItemDtoToReturn()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(itemDtoToReturn.getId()), Long.class))
                .andExpect(jsonPath("$.name", Matchers.is(itemDtoToReturn.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(itemDtoToReturn.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(itemDtoToReturn.getAvailable())))
                .andExpect(jsonPath("$.owner", Matchers.notNullValue()))
                .andExpect(jsonPath("$.requestId", Matchers.is(itemDtoToReturn.getRequestId()), Long.class));

        verify(itemService, times(1))
                           .createItem(any(), any());
*/
    }

    @Test
    void getItemById() throws Exception {
        //Item item =testItem(1);
        ItemDtoWide item = new ItemDtoWide();
        item.setId(1);
        item.setName("name");
        item.setDescription("desc");
        item.setAvailable(true);
        item.setRequest(2);

        when(itemService.getItemWideById(any()))
                .thenReturn(item);

        mockMvc.perform(get("/items/" + item.getId())
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(item.getId()), Integer.class))
                .andExpect(jsonPath("$.name", Matchers.is(item.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(item.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(item.getAvailable())))
                .andExpect(jsonPath("$.request", Matchers.notNullValue()));

        verify(itemService, times(1))
                .getItemWideById(any());
    }

}
