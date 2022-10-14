package ru.practicum.explorewithme.events.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.events.Sort;
import ru.practicum.explorewithme.events.client.EventClient;
import ru.practicum.explorewithme.events.dto.EventDto;
import ru.practicum.explorewithme.events.dto.EventShortDto;
import ru.practicum.explorewithme.events.dto.NewEndpointHit;
import ru.practicum.explorewithme.events.service.PublicEventService;
import ru.practicum.explorewithme.exception.BadRequestException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PublicEventController {
    private final PublicEventService eventService;
    private final EventClient eventClient;
    private static final String APP_NANE = "ewm-main-service";

    @GetMapping
    public List<EventShortDto> getList(
            @RequestParam(value = "text", required = false) String text,
            @RequestParam(value = "categories", required = false) List<@Positive Long> categories,
            @RequestParam(value = "paid", required = false) Boolean paid,
            @RequestParam(value = "rangeStart", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
            @RequestParam(value = "rangeEnd", required = false)
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
            @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "from", defaultValue = "0") Integer from,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            HttpServletRequest request) {
        if (sort != null) {
            Sort.from(sort).orElseThrow(
                    () -> new BadRequestException(String.format("Не известный тип сортировки: %s", sort))
            );
        }
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new BadRequestException("Дата старта должна быть меньше даты конца");
        }
        log.info(
                "Get list events: text - {}, categories - {}, paid - {}, "
                        + "rangeStart - {}, rangeEnd - {}, onlyAvailable - {}, sort - {}, "
                        + " from - {}, size - {}",
                text,
                categories != null ? categories.toString() : null,
                paid,
                rangeStart,
                rangeEnd,
                onlyAvailable,
                sort,
                from,
                size
        );
        sendStats(request);
        return eventService.getList(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size);
    }

    @GetMapping("/{eventId}")
    public EventDto getItem(@Positive @PathVariable("eventId") Long eventId, HttpServletRequest request) {
        log.info("Get event {}", eventId);
        sendStats(request);
        return eventService.getItem(eventId);
    }

    private void sendStats(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (request.getQueryString() != null)
            uri += "?" + request.getQueryString();

        String ip = request.getRemoteAddr();
        if (ip.equals("0:0:0:0:0:0:0:1"))
            ip = "127.0.0.1";

        eventClient.addItem(
                new NewEndpointHit(
                        PublicEventController.APP_NANE,
                        uri,
                        ip,
                        LocalDateTime.now()
                )
        );
    }
}
