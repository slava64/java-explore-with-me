package ru.practicum.explorewithme.requests.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.explorewithme.requests.Request;
import ru.practicum.explorewithme.requests.Status;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Long> {
    // Кол-во запросов для конкретного события
    Long countByEventId(Long eventId);

    // Запросы пользователя
    List<Request> findByRequesterId(Long requesterId, Pageable pageable);

    // Запросы пользователя для события
    List<Request> findByRequesterIdAndEventId(Long requesterId, Long eventId);

    // Запросы события
    List<Request> findByEventId(Long eventId);

    // Запросы события со статусом
    List<Request> findByEventIdAndStatus(Long eventId, Status status);

    // Возвращает один запрос пользователя
    Request findFirstByIdAndRequesterId(Long requestId, Long requesterId);
}
