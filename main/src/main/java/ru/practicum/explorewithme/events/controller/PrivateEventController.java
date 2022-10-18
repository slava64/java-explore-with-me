package ru.practicum.explorewithme.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.events.dto.*;
import ru.practicum.explorewithme.events.service.PrivateEventService;
import ru.practicum.explorewithme.exception.BadRequestException;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PrivateEventController {
    private final PrivateEventService eventService;

    @GetMapping("/{userId}/events")
    public List<EventShortDto> getList(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        log.info("Get list events form {} size {} for user {}", from, size, userId);
        return eventService.getList(userId, from, size);
    }

    @PatchMapping("/{userId}/events")
    public EventDto updateItem(
            @Positive @PathVariable("userId") Long userId,
            @Valid @RequestBody UpdateEventDto updateEventDto
            ) {
        log.info("Update event {} for user {}", updateEventDto.toString(), userId);
        return eventService.updateItem(userId, updateEventDto);
    }

    @PostMapping("/{userId}/events")
    public EventDto addItem(
            @Positive @PathVariable("userId") Long userId,
            @Valid @RequestBody NewEventDto newEventDto
    ) {
        if (newEventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new BadRequestException("Дата события должна быть объявлена не раньше чем за два часа");
        }
        log.info("Add new event {} for user {}", newEventDto.toString(), userId);
        return eventService.addItem(userId, newEventDto);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventDto getItem(
            @Positive @PathVariable("userId") Long userId,
            @Positive @PathVariable("eventId") Long eventId
    ) {
        log.info("Get event {} for user {}", eventId, userId);
        return eventService.getItem(userId, eventId);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventDto cancelItem(
            @Positive @PathVariable("userId") Long userId,
            @Positive @PathVariable("eventId") Long eventId
    ) {
        log.info("Cancel event {} for user {}", eventId, userId);
        return eventService.cancelItem(userId, eventId);
    }

    @PostMapping("/{userId}/events/{eventId}/moderation-comment")
    public ModerationCommentDto addModerationComment(
            @Positive @PathVariable("userId") Long userId,
            @Positive @PathVariable("eventId") Long eventId,
            @Valid @RequestBody NewModerationCommentDto newModerationCommentDto
    ) {
        log.info("Cancel event {} for user {}", eventId, userId);
        return eventService.addModerationComment(userId, eventId, newModerationCommentDto);
    }
}
