package ru.practicum.shareit.request.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ItemRequestServiceTest {
    private final ItemRequestService itemRequestService;
    private final ItemService itemService;

    private final UserService userService;

    private CreateItemRequestDto createItemRequestDto(Integer i) {
        return CreateItemRequestDto.builder()
                .description("Description" + i)
                .build();
    }

    private UserCreateDto createUserDto(Integer i) {
        return UserCreateDto.builder()
                .name("Username" + i)
                .email("mail" + i + "@mail.com")
                .build();
    }

    @Test
    void createItemRequestTest() {
        UserCreateDto createUserDto1 = createUserDto(5432);
        User user1 = userService.addUser(createUserDto1);
        CreateItemRequestDto createItemRequestDto = createItemRequestDto(22);
        createItemRequestDto.setRequester(user1.getId());
        ItemRequestDto itemRequestDto = itemRequestService.createRequest(createItemRequestDto);

        assertEquals(itemRequestDto.getRequester(), user1.getId());
        assertEquals(itemRequestDto.getDescription(), createItemRequestDto.getDescription());
    }

    @Test
    void getItemRequestByIdTest() {
        UserCreateDto createUserDto1 = createUserDto(8576);
        User user1 = userService.addUser(createUserDto1);

        CreateItemRequestDto createItemRequestDto = createItemRequestDto(1);
        createItemRequestDto.setRequester(user1.getId());
        ItemRequestDto itemRequestDto = itemRequestService.createRequest(createItemRequestDto);

        ItemRequestDto itemRequestDtoFinded = itemRequestService.getRequestById(itemRequestDto.getId());

        assertEquals(itemRequestDtoFinded.getId(), itemRequestDto.getId());
        assertEquals(itemRequestDtoFinded.getCreated(), itemRequestDto.getCreated());
        assertEquals(itemRequestDtoFinded.getDescription(), itemRequestDto.getDescription());
        assertEquals(itemRequestDtoFinded.getRequester(), itemRequestDto.getRequester());
    }

    @Test
    void getItemRequestsByRequestorIdTest() {
        UserCreateDto createUserDto1 = createUserDto(234);
        User user1 = userService.addUser(createUserDto1);

        UserCreateDto createUserDto2 = createUserDto(456);
        User user2 = userService.addUser(createUserDto2);

        CreateItemRequestDto createItemRequestDto = createItemRequestDto(2);
        createItemRequestDto.setRequester(user1.getId());
        ItemRequestDto itemRequestDto = itemRequestService.createRequest(createItemRequestDto);

        CreateItemRequestDto createItemRequestDto2 = createItemRequestDto(3);
        createItemRequestDto2.setRequester(user1.getId());
        ItemRequestDto itemRequestDto2 = itemRequestService.createRequest(createItemRequestDto2);

        ItemCreateDto itemCreateDto = ItemCreateDto.builder()
                .name("11")
                .description("222")
                .available(true)
                .owner(user2.getId())
                .requestId(itemRequestDto2.getId())
                .build();
        itemService.createItem(user2.getId(), itemCreateDto);

        List<ItemRequestDto> finded = itemRequestService.getUserRequests(user1.getId());

        assertEquals(finded.size(), 2);
        assertEquals(finded.getLast().getRequester(), user1.getId());
    }


    @Test
    void getAllItemRequestTest() {
        UserCreateDto createUserDto1 = createUserDto(1);
        User user1 = userService.addUser(createUserDto1);
        UserCreateDto createUserDto2 = createUserDto(2);
        User user2 = userService.addUser(createUserDto2);

        CreateItemRequestDto createItemRequestDto1 = createItemRequestDto(222);
        createItemRequestDto1.setRequester(user1.getId());

        ItemRequestDto itemRequestDto1 = itemRequestService.createRequest(createItemRequestDto1);

        CreateItemRequestDto createItemRequestDto2 = createItemRequestDto(22343);
        createItemRequestDto2.setRequester(user1.getId());
        ItemRequestDto itemRequestDto2 = itemRequestService.createRequest(createItemRequestDto2);

        List<ItemRequestDto> finded = itemRequestService.getAllRequests();

        assertEquals(finded.size(), 2);
    }

}
