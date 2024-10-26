package ru.practicum.shareit.booking;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-bookings.
 */
@Data
public class Booking {
    Integer id;
    LocalDateTime start;
    LocalDateTime end;
    Integer item;
    Integer booker;
    BookingStatus status;
}
