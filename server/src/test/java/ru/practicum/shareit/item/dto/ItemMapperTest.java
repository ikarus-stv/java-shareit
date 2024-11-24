package ru.practicum.shareit.item.dto;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.model.Item;

import static org.junit.jupiter.api.Assertions.*;

class ItemMapperTest {

    @Test
    void updateFromUpdateDtoTest() {
        Item item = new Item();
        item.setName("---");
        item.setDescription("---");
        item.setAvailable(null);

        ItemUpdateDto itemUpdateDto = new ItemUpdateDto();
        itemUpdateDto.setName("+");
        itemUpdateDto.setDescription("++");
        itemUpdateDto.setAvailable(true);

        ItemMapper.updateFromUpdateDto(item, itemUpdateDto);

        assertEquals(item.getName(), itemUpdateDto.getName());
        assertEquals(item.getDescription(), itemUpdateDto.getDescription());
        assertEquals(item.getAvailable(), itemUpdateDto.getAvailable());
    }
}