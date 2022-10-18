package ru.practicum.explorewithme.events.repository;

import ru.practicum.explorewithme.events.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepositoryCustom {
    List<Event> getList(
            Integer from,
            Integer size,
            List<Long> users,
            List<String> states,
            List<Long> categories,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd
    );

    List<Event> getPublicList(
            Integer from,
            Integer size,
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            String sort
    );

    List<Event> getListStatusPending(Integer from, Integer size);
}
