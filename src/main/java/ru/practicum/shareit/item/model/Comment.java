package ru.practicum.shareit.item.model;

import lombok.Data;
import ru.practicum.shareit.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String text;

    @ManyToOne
    private Item item;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column
    private LocalDateTime created;
}