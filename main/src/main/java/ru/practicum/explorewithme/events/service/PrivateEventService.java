package ru.practicum.explorewithme.events.service;

import ru.practicum.explorewithme.events.dto.EventDto;
import ru.practicum.explorewithme.events.dto.EventShortDto;
import ru.practicum.explorewithme.events.dto.NewEventDto;
import ru.practicum.explorewithme.events.dto.UpdateEventDto;

import java.util.List;

public interface PrivateEventService {
    List<EventShortDto> getList(Long userId, Integer from, Integer size);

    EventDto updateItem(Long userId, UpdateEventDto updateEventDto);

    EventDto addItem(Long userId, NewEventDto newEventDto);

    EventDto getItem(Long userId, Long eventId);

    EventDto cancelItem(Long userId, Long eventId);
}
