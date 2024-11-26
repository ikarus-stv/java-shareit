package ru.practicum.shareit.user;

import jakarta.persistence.*;
import lombok.*;

/**
 * TODO Sprint add-controllers.
 */

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@ToString

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String email;

    public User() {
    }
}
