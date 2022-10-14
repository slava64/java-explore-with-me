package ru.practicum.explorewithme.events.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.State;
import ru.practicum.explorewithme.events.dto.EventDto;
import ru.practicum.explorewithme.events.dto.EventShortDto;
import ru.practicum.explorewithme.events.mapper.EventMapper;
import ru.practicum.explorewithme.events.repository.EventRepository;
import ru.practicum.explorewithme.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicEventServiceImpl implements PublicEventService{
    private final EventRepository eventRepository;

    @Override
    public List<EventShortDto> getList(
            String text,
            List<Long> categories,
            Boolean paid,
            LocalDateTime rangeStart,
            LocalDateTime rangeEnd,
            Boolean onlyAvailable,
            String sort,
            Integer from,
            Integer size
    ) {
        List<Event> eventList = eventRepository.getPublicList(
                from,
                size,
                text,
                categories,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort
        );
        return EventMapper.toEventShortDto(eventList);
    }

    @Override
    public EventDto getItem(Long eventId) {
        Event event = eventRepository.findFirstByIdAndState(eventId, State.PUBLISHED);
        if (event == null) {
            throw new NotFoundException("Событие не найдено");
        }
        return EventMapper.toEventDto(event);
    }
}
