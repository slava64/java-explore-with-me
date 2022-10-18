package ru.practicum.explorewithme.events.service;

import ru.practicum.explorewithme.events.dto.EventDto;
import ru.practicum.explorewithme.events.dto.ModerationCommentDto;
import ru.practicum.explorewithme.events.dto.NewModerationCommentDto;
import ru.practicum.explorewithme.events.dto.UpdateEventAdminDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminEventService {
    List<EventDto> getList(Integer from, Integer size, List<Long>  users, List<String> states, List<Long> categories,
                           LocalDateTime rangeStart, LocalDateTime rangeEnd);

    List<EventDto> getListStatusPending(Integer from, Integer size);

    EventDto editItem(Long eventId, UpdateEventAdminDto updateEventAdminDto);

    EventDto setPublish(Long eventId);

    EventDto setReject(Long eventId);

    ModerationCommentDto addModerationComment(Long eventId, NewModerationCommentDto newModerationCommentDto);
}
