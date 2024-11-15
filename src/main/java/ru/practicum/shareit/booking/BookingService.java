package ru.practicum.shareit.booking;

import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

@Service
public interface BookingService {

    BookingDto createBooking(BookingCreateDto createDto);

    BookingDto acceptBooking(Integer userId, Integer bookingId, Boolean approved);

    BookingDto getBookingById(Integer userId, Integer bookingId);

    Collection<BookingDto> getBookingsOfUser(Integer userId, RequestBookingStatus bookingStatus);

    Collection<BookingDto> getBookingsByOwner(Integer userId, RequestBookingStatus bookingStatus);
}
