package ru.practicum.shareit.item.mapper;

import org.junit.jupiter.api.Test;
import ru.practicum.shareit.item.dto.ItemMapperByLibrary;

import static org.junit.jupiter.api.Assertions.*;

public class ItemMapperTest {
    @Test
    void nullMappingsTest() {
        ItemMapperByLibrary im = ItemMapperByLibrary.INSTANCE;

        assertNull(im.itemFromCreateDto(null));
        assertNull(im.toItemDto(null));
        assertNull(im.toDtoWide(null));
        assertNull(im.listItem2ListRequestDto(null));
    }
}
