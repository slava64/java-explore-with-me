package ru.practicum.explorewithme.events;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.explorewithme.categories.Category;
import ru.practicum.explorewithme.compilations.Compilation;
import ru.practicum.explorewithme.locations.Location;
import ru.practicum.explorewithme.requests.Request;
import ru.practicum.explorewithme.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events", schema = "public")
@Getter
@Setter
@ToString
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "annotation", nullable = false, length = 2000)
    private String annotation;
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
    @Column(name = "confirmed_requests")
    private Integer confirmedRequests = 0;
    @Column(name = "created_on")
    private LocalDateTime createdOn = LocalDateTime.now();
    @Column(name = "description", nullable = false, length = 7000)
    private String description;
    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;
    @ManyToOne
    @JoinColumn(name = "initiator_id", nullable = false)
    private User initiator;
    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "participant_limit")
    private Integer participantLimit;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private State state = State.PENDING;
    @Column(name = "title", nullable = false, length = 2000)
    private String title;
    @Column(name = "views")
    private Integer views = 0;
    @ManyToMany(mappedBy = "events")
    @ToString.Exclude
    List<Compilation> compilations;
    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    private List<Request> requests;
    @OneToMany(mappedBy = "event")
    @ToString.Exclude
    private List<ModerationComment> moderationComments;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;
        return id != null && id.equals(((Event) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
