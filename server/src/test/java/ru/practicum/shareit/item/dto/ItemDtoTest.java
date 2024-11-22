package ru.practicum.shareit.item.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class ItemDtoTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testSerializeDeserializeItemByRequestDto() throws Exception {
        ItemByRequestDto itemByRequestDto = ItemByRequestDto.builder()
                .id(1)
                .name("Username")
                .owner(1)
                .build();

        String json = objectMapper.writeValueAsString(itemByRequestDto);
        ItemByRequestDto deserializedDto = objectMapper.readValue(json, ItemByRequestDto.class);

        assertEquals(itemByRequestDto.getId(), deserializedDto.getId());
        assertEquals(itemByRequestDto.getName(), deserializedDto.getName());
        assertEquals(itemByRequestDto.getOwner(), deserializedDto.getOwner());
    }

    @Test
    public void testSerializeDeserializeItemCreateDto() throws Exception {
        ItemCreateDto createItemDto = new ItemCreateDto();
        createItemDto.setName("Username");
        createItemDto.setDescription("Description.");
        createItemDto.setAvailable(true);
        createItemDto.setRequestId(1);

        String json = objectMapper.writeValueAsString(createItemDto);
        ItemCreateDto deserializedDto = objectMapper.readValue(json, ItemCreateDto.class);

        assertEquals(createItemDto.getName(), deserializedDto.getName());
        assertEquals(createItemDto.getDescription(), deserializedDto.getDescription());
        assertEquals(createItemDto.getAvailable(), deserializedDto.getAvailable());
        assertEquals(createItemDto.getRequestId(), deserializedDto.getRequestId());
    }


    @Test
    public void testSerializeDeserializeItemDto() throws Exception {
        ItemDto itemDto = ItemDto.builder()
                .id(1)
                .name("Username")
                .description("Description.")
                .available(true)
                .request(2)
                .build();


        String json = objectMapper.writeValueAsString(itemDto);
        ItemDto deserializedDto = objectMapper.readValue(json, ItemDto.class);

        assertEquals(itemDto.getId(), deserializedDto.getId());
        assertEquals(itemDto.getName(), deserializedDto.getName());
        assertEquals(itemDto.getDescription(), deserializedDto.getDescription());
        assertEquals(itemDto.getAvailable(), deserializedDto.getAvailable());
        assertEquals(itemDto.getRequest(), deserializedDto.getRequest());
    }

    @Test
    public void testSerializeDeserializeItemDtoWide() throws Exception {
        ItemDtoWide itemDto = new ItemDtoWide();
        itemDto.setId(1);
        itemDto.setName("Username");
        itemDto.setDescription("Description.");
        itemDto.setAvailable(true);
        itemDto.setRequest(2);

        String json = objectMapper.writeValueAsString(itemDto);
        ItemDtoWide deserializedDto = objectMapper.readValue(json, ItemDtoWide.class);

        assertEquals(itemDto.getId(), deserializedDto.getId());
        assertEquals(itemDto.getName(), deserializedDto.getName());
        assertEquals(itemDto.getDescription(), deserializedDto.getDescription());
        assertEquals(itemDto.getAvailable(), deserializedDto.getAvailable());
        assertEquals(itemDto.getRequest(), deserializedDto.getRequest());
    }

    @Test
    public void testSerializeDeserializeItemUpdateDto() throws Exception {
        ItemUpdateDto updateItemDto = new ItemUpdateDto();
        updateItemDto.setName("Username");
        updateItemDto.setDescription("Description.");
        updateItemDto.setAvailable(true);

        String json = objectMapper.writeValueAsString(updateItemDto);
        ItemUpdateDto deserializedDto = objectMapper.readValue(json, ItemUpdateDto.class);

        assertEquals(updateItemDto.getName(), deserializedDto.getName());
        assertEquals(updateItemDto.getDescription(), deserializedDto.getDescription());
        assertEquals(updateItemDto.getAvailable(), deserializedDto.getAvailable());
    }

}
