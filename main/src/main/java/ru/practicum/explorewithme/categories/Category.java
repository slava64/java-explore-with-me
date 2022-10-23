package ru.practicum.explorewithme.categories;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.events.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories", schema = "public")
@Getter
@Setter
@ToString
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @OneToMany(mappedBy = "category")
    @ToString.Exclude
    private List<Event> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        return id != null && id.equals(((Category) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
