package ru.practicum.explorewithme.users;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.requests.Request;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users", schema = "public")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "email", nullable = false, length = 254, unique = true)
    private String email;
    @OneToMany(mappedBy = "initiator")
    @ToString.Exclude
    private List<Event> events;
    @OneToMany(mappedBy = "requester")
    @ToString.Exclude
    private List<Request> requests;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return id != null && id.equals(((User) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}