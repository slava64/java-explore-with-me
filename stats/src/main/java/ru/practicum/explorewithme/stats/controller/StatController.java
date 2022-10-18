package ru.practicum.explorewithme.stats.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.stats.dto.EndpointHit;
import ru.practicum.explorewithme.stats.dto.NewEndpointHit;
import ru.practicum.explorewithme.stats.dto.ViewStats;
import ru.practicum.explorewithme.stats.service.StatService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StatController {
    private final StatService statService;

    @GetMapping("/stats")
    public List<ViewStats> getList(
            @RequestParam(value = "start")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
            @RequestParam(value = "end")
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
            @RequestParam(value = "uris", required = false) List<String> uris,
            @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique
    ) {
        log.info("Find stat by params: start - {}, end - {}, uris - {}, unique - {}",
                start, end, uris != null ? uris.toString() : null, unique);
        return statService.getList(start, end, uris, unique);
    }

    @PostMapping("/hit")
    public EndpointHit addItem(@RequestBody NewEndpointHit newEndpointHit) {
        log.info("Add new item {}", newEndpointHit.toString());
        return statService.addItem(newEndpointHit);
    }

}
