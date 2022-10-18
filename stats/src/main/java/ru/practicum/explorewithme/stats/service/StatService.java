package ru.practicum.explorewithme.stats.service;

import ru.practicum.explorewithme.stats.dto.EndpointHit;
import ru.practicum.explorewithme.stats.dto.NewEndpointHit;
import ru.practicum.explorewithme.stats.dto.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatService {
    List<ViewStats> getList(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);

    EndpointHit addItem(NewEndpointHit newEndpointHit);
}
