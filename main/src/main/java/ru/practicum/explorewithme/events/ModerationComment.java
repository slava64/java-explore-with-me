package ru.practicum.explorewithme.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "moderation_comments", schema = "public")
@Getter
@Setter
@ToString
public class ModerationComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "commentator_id", nullable = false)
    private User commentator;
    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    @Column(name = "description", nullable = false, length = 7000)
    private String description;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
