package ru.practicum.explorewithme.events.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.State;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, EventRepositoryCustom {
    // Выборка событий по id пользователя
    List<Event> findByInitiatorId(Long userId, Pageable pageable);

    // Выборка событий по id и событию
    Event findFirstByIdAndState(Long eventId, State state);
}
