package ru.practicum.shareit.request;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * TODO Sprint add-item-requests.
 */
@Entity
@Table(name = "requests", schema = "public")
@Getter
@Setter
@ToString

public class ItemRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String description;
    @Column
    private Integer requester;
    @Column
    private LocalDateTime created;
}
