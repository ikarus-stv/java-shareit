package ru.practicum.shareit.item.service;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserCreateDto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemServiceImplTest {

    private final UserService userService;
    private final ItemService itemService;
    private final ItemRepository itemRepository;
    private final BookingService bookingService;


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
    void createItemTest() {
        UserCreateDto userCreateDto = newUserCreateDto("OneCreate");
        User user = userService.addUser(userCreateDto);
        ItemCreateDto itemCreateDtoOne = newItemCreateDto("Item_OneCreate");
        //ItemDto itemDto1 = ItemMapperByLibrary.INSTANCE.toItemDto(itemService.createItem(user.getId(), itemCreateDtoOne));
        Item item = itemService.createItem(user.getId(), itemCreateDtoOne);

        Item fromBase = itemRepository.findById(item.getId()).get();
        assertNotNull(fromBase);
        assertEquals(item.getName(), fromBase.getName());
        assertEquals(item.getDescription(), fromBase.getDescription());
        assertEquals(item.getAvailable(), fromBase.getAvailable());
    }

    @Test
    void updateItemTest() {
        UserCreateDto userCreateDto = newUserCreateDto("OneUpdate");
        User user = userService.addUser(userCreateDto);
        ItemCreateDto itemCreateDtoOne = newItemCreateDto("Item_OneUpdate");
        Item item = itemService.createItem(user.getId(), itemCreateDtoOne);

        ItemUpdateDto itemUpdateDto =  new ItemUpdateDto();
        itemUpdateDto.setName(item.getName() + "_upd");
        itemUpdateDto.setDescription(item.getDescription() + "_upd");
        itemUpdateDto.setAvailable(!item.getAvailable());

        Integer userid = user.getId();
        Integer itemId = item.getId();
        itemService.updateItem(userid, itemId, itemUpdateDto);

        Item fromBase = itemRepository.findById(item.getId()).get();

        assertNotNull(fromBase);
        assertEquals(itemUpdateDto.getName(), fromBase.getName());
        assertEquals(itemUpdateDto.getDescription(), fromBase.getDescription());
        assertEquals(itemUpdateDto.getAvailable(), fromBase.getAvailable());
    }

    @Test
    void getUserItemsTest() {
        UserCreateDto userCreateDto = newUserCreateDto("Onegetuser");
        User user = userService.addUser(userCreateDto);
        ItemCreateDto itemCreateDtoOne = newItemCreateDto("Item_Onegetuser");
        ItemDto itemDto1 = ItemMapperByLibrary.INSTANCE.toItemDto(itemService.createItem(user.getId(), itemCreateDtoOne));
        ItemCreateDto itemCreateDtoTwo = newItemCreateDto("Item_Twogetuser");
        ItemDto itemDto2 = ItemMapperByLibrary.INSTANCE.toItemDto(itemService.createItem(user.getId(), itemCreateDtoTwo));

        Collection<Item> itemsFromDB = itemService.getItemsByUser(user.getId());
        assertEquals(itemsFromDB.size(),2);
        assertTrue(itemsFromDB.stream().anyMatch(i -> i.getId().equals(itemDto1.getId())));
        assertTrue(itemsFromDB.stream().anyMatch(i -> i.getId().equals(itemDto2.getId())));
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

    @Test
    void createCommentTest() {
        UserCreateDto createUserDto1 = newUserCreateDto("createCommentTest1");
        User user1 = userService.addUser(createUserDto1);

        UserCreateDto createUserDto2 = newUserCreateDto("createCommentTest22");
        User user2 = userService.addUser(createUserDto2);

        ItemCreateDto createItemDto1 = newItemCreateDto("createCommentTest--1");
        Item item1 = itemService.createItem(user1.getId(), createItemDto1);

        BookingCreateDto bookingDto = BookingCreateDto.builder()
                .start(LocalDateTime.of(2020, 11, 18, 11, 11, 11))
                .end(LocalDateTime.of(2021, 11, 18, 11, 11, 11))
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();
        BookingDto createdBookingDto = bookingService.createBooking(bookingDto);

        BookingDto approvedBookingDto = bookingService.acceptBooking(user1.getId(),
                createdBookingDto.getId(), true);

        CommentCreateDto createCommentDto = CommentCreateDto.builder()
                .text("Text.")
                .build();
        CommentDto commentDto = itemService.createComment(user2.getId(), item1.getId(), createCommentDto);

        assertEquals(commentDto.getText(), createCommentDto.getText());
        assertEquals(commentDto.getAuthorName(), user2.getName());

        assertThrows(ValidationException.class, () -> itemService.createComment(-100, -200, createCommentDto));

    }

    @Test
    void getItemByIdWideTest() {
        UserCreateDto createUserDto1 = newUserCreateDto("getItemByIdWideTest1");
        User user1 = userService.addUser(createUserDto1);

        UserCreateDto createUserDto2 = newUserCreateDto("getItemByIdWideTest22");
        User user2 = userService.addUser(createUserDto2);

        ItemCreateDto createItemDto1 = newItemCreateDto("getItemByIdWideTest--1");
        Item item1 = itemService.createItem(user1.getId(), createItemDto1);

        BookingCreateDto bookingDto = BookingCreateDto.builder()
                .start(LocalDateTime.of(2020, 11, 18, 11, 11, 11))
                .end(LocalDateTime.of(2021, 11, 18, 11, 11, 11))
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();
        BookingDto createdBookingDto = bookingService.createBooking(bookingDto);

        BookingDto approvedBookingDto = bookingService.acceptBooking(user1.getId(),
                createdBookingDto.getId(), true);

        CommentCreateDto createCommentDto = CommentCreateDto.builder()
                .text("Text.")
                .build();
        CommentDto commentDto = itemService.createComment(user2.getId(), item1.getId(), createCommentDto);

        ItemDtoWide itemWide = itemService.getItemWideById(item1.getId());


        assertEquals(itemWide.getId(), item1.getId());
        assertEquals(itemWide.getName(), item1.getName());
        assertEquals(itemWide.getDescription(), item1.getDescription());

        assertEquals(itemWide.getComments().size(), 1);
        assertEquals(itemWide.getComments().getFirst().getText(), createCommentDto.getText());
        assertEquals(itemWide.getComments().getFirst().getAuthorName(), user2.getName());

        assertThrows(NotFoundException.class, () -> itemService.getItemWideById(-100));

    }

}
