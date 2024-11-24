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
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.dto.ItemDtoWide;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;


import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

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

    private CommentDto testCommentDto(Integer i) {
        return CommentDto.builder()
                .id(i)
                .text("Text" + i)
                .created(LocalDateTime.now())
                .authorName("Username" + i)
                .build();
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
    }


    @Test
    void updateItemTest() throws Exception {
        Item item = testItem(2);

        ItemUpdateDto updateDto = new ItemUpdateDto();
        updateDto.setName("1");
        updateDto.setDescription("2");
        updateDto.setAvailable(true);

        when(itemService.updateItem(any(), any(), any()))
                .thenReturn(item);

        mockMvc.perform(patch("/items/" + item.getId())
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(updateDto))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(item.getId()), Integer.class))
                .andExpect(jsonPath("$.name", Matchers.is(item.getName())))
                .andExpect(jsonPath("$.description", Matchers.is(item.getDescription())))
                .andExpect(jsonPath("$.available", Matchers.is(item.getAvailable())))
                .andExpect(jsonPath("$.owner", Matchers.notNullValue()))
                .andExpect(jsonPath("$.requestId", Matchers.is(item.getRequestId()), Integer.class));

        verify(itemService, times(1))
                .updateItem(any(), any(), any());
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

    @Test
    void getItemsByUserIdTest() throws Exception {
        List<Item> itemList = List.of(testItem(1),
                testItem(2));

        when(itemService.getItemsByUser(any()))
                .thenReturn(itemList);

        mockMvc.perform(get("/items")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(itemList.get(0).getId()), Integer.class))
                .andExpect(jsonPath("$[0].name", Matchers.is(itemList.get(0).getName())))
                .andExpect(jsonPath("$[0].description", Matchers.is(
                        itemList.get(0).getDescription())))
                .andExpect(jsonPath("$[1].id", Matchers.is(itemList.get(1).getId()), Integer.class))
                .andExpect(jsonPath("$[1].name", Matchers.is(itemList.get(1).getName())))
                .andExpect(jsonPath("$[1].description", Matchers.is(
                        itemList.get(1).getDescription())));

        verify(itemService, times(1))
                .getItemsByUser(any());
    }



    @Test
    void searchItemsTest() throws Exception {
        List<Item> itemList = List.of(testItem(1),
                testItem(2));

        when(itemService.getItemsByText(any()))
                .thenReturn(itemList);

        mockMvc.perform(get("/items/search?text=text")
                        .header("X-Sharer-User-Id", 1)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", Matchers.is(itemList.get(0).getId()), Integer.class))
                .andExpect(jsonPath("$[0].name", Matchers.is(itemList.get(0).getName())))
                .andExpect(jsonPath("$[0].description", Matchers.is(
                        itemList.get(0).getDescription())))
                .andExpect(jsonPath("$[1].id", Matchers.is(itemList.get(1).getId()), Integer.class))
                .andExpect(jsonPath("$[1].name", Matchers.is(itemList.get(1).getName())))
                .andExpect(jsonPath("$[1].description", Matchers.is(
                        itemList.get(1).getDescription())));

        verify(itemService, times(1))
                .getItemsByText(any());
    }


    @Test
    void createCommentTest() throws Exception {
        CommentDto commentDto = testCommentDto(1);
        when(itemService.createComment(any(), any(), any()))
                .thenReturn(commentDto);

        mockMvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", 1)
                        .content(objectMapper.writeValueAsString(new CommentDto()))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(commentDto.getId()), Integer.class))
                .andExpect(jsonPath("$.text", Matchers.is(commentDto.getText())))
                .andExpect(jsonPath("$.authorName", Matchers.is(commentDto.getAuthorName())))
                .andExpect(jsonPath("$.created", Matchers.containsString(
                        commentDto.getCreated().toString().substring(0, 25))));

        verify(itemService, times(1))
                .createComment(any(), any(), any());
    }


}
