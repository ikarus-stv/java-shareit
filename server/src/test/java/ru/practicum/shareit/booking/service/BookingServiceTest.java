package ru.practicum.shareit.booking.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.BookingStatus;
import ru.practicum.shareit.booking.RequestBookingStatus;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.request.dto.CreateItemRequestDto;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserCreateDto;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class BookingServiceTest {
    private final ItemRequestService itemRequestService;
    private final UserService userService;
    private final ItemService itemService;
    private final BookingService bookingService;

    private String all = "ALL";
    private String current = "CURRENT";
    private String past = "PAST";
    private String future = "FUTURE";
    private String waiting = "WAITING";
    private String rejected = "REJECTED";
    private String mistake = "Mistake";

    private LocalDateTime start = LocalDateTime.of(2025, 11, 18, 11, 11, 11);
    private LocalDateTime end = LocalDateTime.of(2025, 11, 18, 11, 11, 12);

    private ItemCreateDto createItemDto(Integer i) {
        return ItemCreateDto.builder()
                .name("Itemname" + i)
                .description("Description" + i)
                .available(true)
                .build();
    }

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
    void createBookingTest() {
        UserCreateDto createUserDto1 = createUserDto(1);
        User user1 = userService.addUser(createUserDto1);
        UserCreateDto createUserDto2 = createUserDto(2);
        User user2 = userService.addUser(createUserDto2);

        ItemCreateDto createItemDto1 = createItemDto(1);
        Item item1 = itemService.createItem(user1.getId(), createItemDto1);
        ItemCreateDto createItemDto2 = createItemDto(2);

        ItemCreateDto createItemDtoNotAvailable = ItemCreateDto.builder()
                .name("Itemname")
                .description("Description")
                .available(false)
                .build();

        Item itemNotAvailable = itemService.createItem(user1.getId(), createItemDtoNotAvailable);

        BookingCreateDto bookingCreateDto = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(item1.getId())
                .booker(-5)
                .build();

        BookingCreateDto bookingCreateDto2 = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(-6)
                .booker(user2.getId())
                .build();

        BookingCreateDto bookingCreateDtoNotAvailable = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(itemNotAvailable.getId())
                .booker(user2.getId())
                .build();

        BookingCreateDto bookingCreateDtoEqualDates = BookingCreateDto.builder()
                .start(end)
                .end(end)
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();


        assertThrows(NotFoundException.class, () -> bookingService.createBooking(bookingCreateDto));
        assertThrows(NotFoundException.class, () -> bookingService.createBooking(bookingCreateDto2));
        assertThrows(ValidationException.class, () -> bookingService.createBooking(bookingCreateDtoNotAvailable));
        assertThrows(ValidationException.class, () -> bookingService.createBooking(bookingCreateDtoEqualDates));

        bookingCreateDto.setBooker(user2.getId());
        BookingDto bookingDto = bookingService.createBooking(bookingCreateDto);

        assertEquals(bookingDto.getItem().getId(), bookingCreateDto.getItemId());
        assertEquals(bookingDto.getStart(), bookingDto.getStart());
        assertEquals(bookingDto.getEnd(), bookingDto.getEnd());
        assertEquals(bookingDto.getBooker().getId(), user2.getId());
        assertEquals(bookingDto.getStatus(), BookingStatus.WAITING);
    }


    @Test
    void updateBookingTest() {
        UserCreateDto createUserDto1 = createUserDto(1);
        User user1 = userService.addUser(createUserDto1);
        UserCreateDto createUserDto2 = createUserDto(2);
        User user2 = userService.addUser(createUserDto2);
        ItemCreateDto createItemDto = createItemDto(1);
        Item item = itemService.createItem(user1.getId(), createItemDto);
        ItemCreateDto createItemDto2 = createItemDto(2);
        Item item2 = itemService.createItem(user1.getId(), createItemDto2);

        BookingCreateDto bookingCreateDto = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(item.getId())
                .booker(user2.getId())
                .build();

        BookingDto bookingDto = bookingService.createBooking(bookingCreateDto);

        BookingCreateDto bookingCreateDto2 = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(item2.getId())
                .booker(user2.getId())
                .build();

        BookingDto bookingDto2 = bookingService.createBooking(bookingCreateDto2);

        assertThrows(NotFoundException.class, () -> bookingService.acceptBooking(user1.getId(),
                -100, true));
        assertThrows(ValidationException.class, () -> bookingService.acceptBooking(user2.getId(),
                bookingDto.getId(), false));


        BookingDto notApproved = bookingService.acceptBooking(user1.getId(), bookingDto.getId(), false);
        assertEquals(notApproved.getStatus(), BookingStatus.REJECTED);

        BookingDto approved = bookingService.acceptBooking(user1.getId(), bookingDto2.getId(), true);
        assertEquals(approved.getStatus(), BookingStatus.APPROVED);
    }

    @Test
    void getBookingByIdTest() {
        UserCreateDto createUserDto1 = createUserDto(1);
        User user1 = userService.addUser(createUserDto1);
        UserCreateDto createUserDto2 = createUserDto(2);
        User user2 = userService.addUser(createUserDto2);
        UserCreateDto createUserDto3 = createUserDto(3);
        User user3 = userService.addUser(createUserDto3);

        ItemCreateDto createItemDto1 = createItemDto(1);
        Item item1 = itemService.createItem(user1.getId(), createItemDto1);
        ItemCreateDto createItemDto2 = createItemDto(2);
        Item item2 = itemService.createItem(user1.getId(), createItemDto2);

        BookingCreateDto bookingDto = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();

        BookingCreateDto bookingDto2 = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(item2.getId())
                .build();

        BookingDto returnBookingDto = bookingService.createBooking(bookingDto);

        BookingDto finded = bookingService.getBookingById(user1.getId(), returnBookingDto.getId());

        assertEquals(finded.getId(), returnBookingDto.getId());
        assertThrows(NotFoundException.class, () -> bookingService.getBookingById(user1.getId(), -100));
        assertThrows(ValidationException.class, () -> bookingService.getBookingById(-100, returnBookingDto.getId()));
        assertThrows(ValidationException.class, () -> bookingService.getBookingById(user3.getId(), returnBookingDto.getId()));
    }


    @Test
    void getBookingsTest() {
        UserCreateDto createUserDto1 = createUserDto(1);
        User user1 = userService.addUser(createUserDto1);
        UserCreateDto createUserDto2 = createUserDto(2);
        User user2 = userService.addUser(createUserDto2);

        ItemCreateDto createItemDto1 = createItemDto(1);
        Item item1 = itemService.createItem(user1.getId(), createItemDto1);

        BookingCreateDto bookingCreateDtoCurrent = BookingCreateDto.builder()
                .start(LocalDateTime.of(2020, 11, 18, 11, 11, 11))
                .end(end)
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();
        bookingService.createBooking(bookingCreateDtoCurrent);

        BookingCreateDto bookingCreateDtoFuture = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();
        bookingService.createBooking(bookingCreateDtoFuture);

        BookingCreateDto bookingCreateDtoPast = BookingCreateDto.builder()
                .start(LocalDateTime.of(2019, 11, 15, 10, 10, 10))
                .end(LocalDateTime.of(2020, 11, 15, 12, 12, 12))
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();
        bookingService.createBooking(bookingCreateDtoPast);

        Collection<BookingDto> allList = bookingService.getBookingsOfUser(user2.getId(), RequestBookingStatus.ALL);

        assertEquals(allList.size(), 3);
    }

    @Test
    void getAllByOwnerTest() {
        UserCreateDto createUserDto1 = createUserDto(1);
        User user1 = userService.addUser(createUserDto1);
        UserCreateDto createUserDto2 = createUserDto(2);
        User user2 = userService.addUser(createUserDto2);

        ItemCreateDto createItemDto1 = createItemDto(1);
        Item item1 = itemService.createItem(user1.getId(), createItemDto1);

        BookingCreateDto bookingDtoCurrent = BookingCreateDto.builder()
                .start(LocalDateTime.of(2020, 11, 18, 11, 11, 11))
                .end(end)
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();

        bookingService.createBooking(bookingDtoCurrent);

        BookingCreateDto bookingDtoFuture = BookingCreateDto.builder()
                .start(start)
                .end(end)
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();
        bookingService.createBooking(bookingDtoFuture);

        BookingCreateDto bookingDtoPast = BookingCreateDto.builder()
                .start(LocalDateTime.of(2019, 11, 15, 10, 10, 10))
                .end(LocalDateTime.of(2020, 11, 15, 12, 12, 12))
                .itemId(item1.getId())
                .booker(user2.getId())
                .build();
        bookingService.createBooking(bookingDtoPast);

        Collection<BookingDto> allList = bookingService.getBookingsByOwner(user1.getId(), RequestBookingStatus.ALL);

        assertEquals(allList.size(), 3);
        assertThrows(InvalidUserException.class, () -> bookingService.getBookingsByOwner(-100, RequestBookingStatus.ALL));
    }
}
