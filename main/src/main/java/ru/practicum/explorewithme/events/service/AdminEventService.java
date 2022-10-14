package ru.practicum.explorewithme.events.service;

import ru.practicum.explorewithme.events.State;
import ru.practicum.explorewithme.events.dto.EventDto;
import ru.practicum.explorewithme.events.dto.NewEventDto;
import ru.practicum.explorewithme.events.dto.UpdateEventAdminDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<EventDto> getList(Integer from, Integer size, List<Long>  users, List<String> states, List<Long> categories,
                           LocalDateTime rangeStart, LocalDateTime rangeEnd);

    EventDto editItem(Long eventId, UpdateEventAdminDto updateEventAdminDto);

    EventDto setPublish(Long eventId);

    EventDto setReject(Long eventId);
}
