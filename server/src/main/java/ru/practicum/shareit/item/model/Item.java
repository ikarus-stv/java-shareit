package ru.practicum.shareit.item.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * TODO Sprint add-controllers.
 */

@Entity
@Table(name = "items", schema = "public")
@Getter
@Setter
@ToString

public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private Boolean available;
    @Column
    private Integer owner;
    @Column(name = "request")
    private Integer requestId;
}
