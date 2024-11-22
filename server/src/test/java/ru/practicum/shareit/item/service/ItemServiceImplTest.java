package ru.practicum.shareit.item.service;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemMapperByLibrary;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserCreateDto;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceImplTest {

    private final UserService userService;
    private final ItemService itemService;

    private UserCreateDto newUserCreateDto(String namePattern) {
        UserCreateDto result = new UserCreateDto();
        result.setName("Name_" + namePattern);
        result.setEmail(namePattern + "@domain.ru");
        return result;
    }

    private ItemCreateDto newItemCreateDto(String name) {
        ItemCreateDto result = new ItemCreateDto();
        result.setName(name);
        result.setDescription("desc: " + name);
        result.setAvailable(true);
        return result;
    }

    @Test
    void getUserItemsTest() {
        UserCreateDto userCreateDto = newUserCreateDto("One");
        User user = userService.addUser(userCreateDto);
        ItemCreateDto itemCreateDtoOne = newItemCreateDto("Item_One");
        ItemDto itemDto1 = ItemMapperByLibrary.INSTANCE.toItemDto(itemService.createItem(user.getId(), itemCreateDtoOne));
        ItemCreateDto itemCreateDtoTwo = newItemCreateDto("Item_Two");
        ItemDto itemDto2 = ItemMapperByLibrary.INSTANCE.toItemDto(itemService.createItem(user.getId(), itemCreateDtoTwo));

        Collection<Item> itemsFromDB = itemService.getItemsByUser(user.getId());
        assertEquals(itemsFromDB.size(),2);
        assertTrue(itemsFromDB.stream().filter(i -> i.getId().equals(itemDto1.getId())).findAny().isPresent());
        assertTrue(itemsFromDB.stream().filter(i -> i.getId().equals(itemDto2.getId())).findAny().isPresent());
    }

    @Test
    void getItemsByTextTest() {
        String itemName = "getItemsByText_Name";
        UserCreateDto userCreateDto = newUserCreateDto("getItemsByText_User");
        User user = userService.addUser(userCreateDto);
        ItemCreateDto itemCreateDtoOne = newItemCreateDto(itemName);
        Item item1 = itemService.createItem(user.getId(), itemCreateDtoOne);

        Collection<Item> itemsByText = itemService.getItemsByText(itemName);
        Optional<Item> any = itemsByText.stream().filter(i -> i.getId().equals(item1.getId())).findAny();
        assertTrue(any.isPresent());
        Item founded = any.get();
        assertEquals(item1.getName(), founded.getName());
        assertEquals(item1.getDescription(), founded.getDescription());
        assertEquals(item1.getOwner(), founded.getOwner());
    }


}
