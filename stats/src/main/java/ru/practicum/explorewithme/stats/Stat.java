package ru.practicum.explorewithme.stats;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stats", schema = "public")
@Getter
@Setter
@ToString
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "app", nullable = false, length = 50)
    private String app;
    @Column(name = "uri", nullable = false, length = 255)
    private String uri;
    @Column(name = "ip", nullable = false, length = 50)
    private String ip;
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Stat)) return false;
        return id != null && id.equals(((Stat) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
