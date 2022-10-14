package ru.practicum.explorewithme.compilations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.events.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "compilations", schema = "public")
@Getter
@Setter
@ToString
public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 255)
    private String name;
    @Column(name = "pinned", nullable = false)
    private Boolean pinned;
    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id"))
    @ToString.Exclude
    List<Event> events;

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
