package ru.practicum.shareit.booking.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


import java.time.LocalDateTime;

@Data
public class BookingCreateDto {
    @NotNull
    @Future
    private LocalDateTime start;
    @NotNull
    @Future
    private LocalDateTime end;
    @NotNull
    private Integer itemId;
    private Integer booker;
}
