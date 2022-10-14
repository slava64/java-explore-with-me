package ru.practicum.explorewithme.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.compilations.Compilation;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.State;
import ru.practicum.explorewithme.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "participation_requests", schema = "public")
@Getter
@Setter
@ToString
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "requester_id")
    private User requester;
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Compilation)) return false;
        return id != null && id.equals(((Compilation) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
