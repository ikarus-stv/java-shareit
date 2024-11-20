package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.Collection;

/**
 * TODO Sprint add-bookings.
 */
@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;

    @PostMapping
    BookingDto createBooking(@RequestHeader("X-Sharer-User-Id") Integer userId, @Valid @RequestBody BookingCreateDto createDto) {
        createDto.setBooker(userId);
        return service.createBooking(createDto);
    }

    @PatchMapping("/{bookingId}")
    BookingDto acceptBooking(@RequestHeader("X-Sharer-User-Id") Integer userId, @PathVariable Integer bookingId, @RequestParam Boolean approved) {
        return service.acceptBooking(userId, bookingId, approved);
    }

    //GET /bookings?state={state}
    @GetMapping
    Collection<BookingDto> getBookingsOfUser(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                             @RequestParam(required = false, defaultValue = "ALL")
                                             RequestBookingStatus bookingStatus) {
        return service.getBookingsOfUser(userId, bookingStatus);
    }

    @GetMapping("/{bookingId}")
    BookingDto getBookingById(@RequestHeader("X-Sharer-User-Id") Integer userId, @PathVariable Integer bookingId) {
        return service.getBookingById(userId, bookingId);
    }

    //GET /bookings/owner?state={state}
    @GetMapping("/owner")
    Collection<BookingDto> getBookingsByOwner(@RequestHeader("X-Sharer-User-Id") Integer userId,
                                             @RequestParam(required = false, defaultValue = "ALL")
                                             RequestBookingStatus bookingStatus) {
        return service.getBookingsByOwner(userId, bookingStatus);
    }

}
