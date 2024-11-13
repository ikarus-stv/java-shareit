package ru.practicum.shareit.booking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.dto.ItemMapperByLibrary;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.user.dto.UserMapper;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookingMapper {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "item", source = "itemId")
    @Mapping(target = "status", constant = "WAITING")
    abstract Booking fromCreateDto(BookingCreateDto bookingCreateDto);

    BookingDto bookingToDto(Booking booking) {

        if (booking == null) {
            return null;
        }

        BookingDto bookingDto = new BookingDto();

        bookingDto.setId(booking.getId());
        bookingDto.setStart(booking.getStart());
        bookingDto.setEnd(booking.getEnd());

        User booker = userRepository.findById(booking.getBooker())
                .orElseThrow(() -> new NotFoundException("Пользователь с id=" + booking.getBooker() + " не найден"));

        bookingDto.setBooker(UserMapper.toUserDto(booker));

        Item item = itemRepository.findById(booking.getItem())
                .orElseThrow(() -> new NotFoundException("Итем с id=" + booking.getItem() + " не найден"));
        bookingDto.setItem(ItemMapperByLibrary.INSTANCE.toItemDto(item));

        bookingDto.setStatus(booking.getStatus());

        return bookingDto;

    }

    abstract Collection<BookingDto> toListOfBookingDto(List<Booking> list);
}
