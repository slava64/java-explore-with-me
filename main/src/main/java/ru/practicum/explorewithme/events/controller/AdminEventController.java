package ru.practicum.explorewithme.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.events.State;
import ru.practicum.explorewithme.events.dto.EventDto;
import ru.practicum.explorewithme.events.dto.UpdateEventAdminDto;
import ru.practicum.explorewithme.events.service.AdminEventService;
import ru.practicum.explorewithme.exception.BadRequestException;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@Slf4j
@RequiredArgsConstructor
@Validated
public class AdminEventController {
    private final AdminEventService eventService;

    @GetMapping
    public List<EventDto> getList(
            @RequestParam(value = "users", required = false) List<@Positive Long> users,
            @RequestParam(value = "states", required = false) List<String> states,
            @RequestParam(value = "categories", required = false) List<@Positive Long> categories,
            @RequestParam(value = "rangeStart", required = false)
            LocalDateTime rangeStart,
            @RequestParam(value = "rangeEnd", required = false)
            LocalDateTime rangeEnd,
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size
    ) {
        if (states != null) {
            for (String state : states) {
                State.from(state).orElseThrow(
                        () -> new BadRequestException(String.format("Не известный статус: %s", state))
                );
            }
        }
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Дата старта должна быть меньше даты конца");
        }
        log.info(
                "Get list events: users - {}, states - {}, categories - {}, "
                + "rangeStart - {}, rangeEnd - {}, from - {}, size - {}",
                users != null ? users.toString() : null,
                states != null ? states.toString() : null,
                categories != null ? categories.toString() : null,
                rangeStart,
                rangeEnd,
                from,
                size
        );
        return eventService.getList(from, size, users, states, categories, rangeStart, rangeEnd);
    }

    @PutMapping("/{eventId}")
    public EventDto editItem(
            @PathVariable("eventId") @Positive Long eventId,
            @RequestBody UpdateEventAdminDto updateEventAdminDto) {
        log.info("Edit event data {}, for eventId - {}", updateEventAdminDto.toString(), eventId);
        return eventService.editItem(eventId, updateEventAdminDto);
    }

    @PatchMapping("/{eventId}/publish")
    public EventDto setPublish(@Positive @PathVariable("eventId") Long eventId) {
        log.info("Set publish for eventId {}", eventId);
        return eventService.setPublish(eventId);
    }

    @PatchMapping("/{eventId}/reject")
    public EventDto setReject(@Positive @PathVariable("eventId") Long eventId) {
        log.info("Set publish for eventId {}", eventId);
        return eventService.setReject(eventId);
    }
}
