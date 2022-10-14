package ru.practicum.explorewithme.stats.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.stats.Stat;
import ru.practicum.explorewithme.stats.dto.EndpointHit;
import ru.practicum.explorewithme.stats.dto.NewEndpointHit;
import ru.practicum.explorewithme.stats.dto.ViewStats;
import ru.practicum.explorewithme.stats.mapper.StatMapper;
import ru.practicum.explorewithme.stats.repository.StatRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class StatServiceImpl implements StatService {
    private final StatRepository statRepository;

    @Override
    public List<ViewStats> getList(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique) {
        List<Object[]> stats = statRepository.getList(start, end, uris, unique);
        return StatMapper.toViewStats(stats);
    }

    @Override
    public EndpointHit addItem(NewEndpointHit newEndpointHit) {
        Stat stat = StatMapper.toStat(newEndpointHit);
        Stat savedStat = statRepository.save(stat);
        return StatMapper.toEndpointHit(savedStat);
    }
}
