package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.exception.InvalidUserException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.exception.ValidationException;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final BookingMapper mapper;

    @Override
    public BookingDto createBooking(BookingCreateDto createDto) {
        Integer userId = createDto.getBooker();
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Нет пользователя с id=" + userId));

        Integer itemId = createDto.getItemId();
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("Итем с id=" + itemId + " не найден"));

        if (createDto.getStart().equals(createDto.getEnd()) ||  createDto.getStart().isAfter(createDto.getEnd())) {
            throw new ValidationException("Время начала должно быть меньше времени конца");
        }

        if (!item.getAvailable()) {
            throw new ValidationException("Unavailable Item");
        }


        Booking booking = mapper.fromCreateDto(createDto);
        repository.save(booking);
        return mapper.bookingToDto(booking);
    }

    @Override
    public BookingDto acceptBooking(Integer userId, Integer bookingId, Boolean approved) {
        Booking booking = repository.findById(bookingId).orElseThrow(() -> new NotFoundException("Букинг с id=" + bookingId + " не найден"));
        Item item = itemRepository.findById(booking.getItem()).get();
        if (!item.getOwner().equals(userId)) {
            throw new ValidationException("acceptBooking может вызывать только владелец!");
        }
        booking.setStatus(approved ? BookingStatus.APPROVED : BookingStatus.REJECTED);
        repository.save(booking);
        return mapper.bookingToDto(booking);
    }

    @Override
    public BookingDto getBookingById(Integer userId, Integer bookingId) {
        Booking booking = repository.findById(bookingId).orElseThrow(() -> new NotFoundException("Букинг с id=" + bookingId + " не найден"));
        Item item = itemRepository.findById(booking.getItem()).get();
        if (!userId.equals(booking.getBooker()) && !userId.equals(item.getOwner())) {
            throw new ValidationException("getBookingById может вызывать только владелец вещи или инициатор букинга!");
        }
        return mapper.bookingToDto(booking);
    }

    @Override
    public Collection<BookingDto> getBookingsOfUser(Integer userId, RequestBookingStatus bookingStatus) {
        List<Booking> list = repository.getByBooker(userId);
        return mapper.toListOfBookingDto(list);
    }

    @Override
    public Collection<BookingDto> getBookingsByOwner(Integer userId, RequestBookingStatus bookingStatus) {
        List<Booking> list = repository.getByOwner(userId);
        if (list.isEmpty()) {
            throw new InvalidUserException("Неправильный пользователь = " + userId);
        }
        return mapper.toListOfBookingDto(list);
    }
}
