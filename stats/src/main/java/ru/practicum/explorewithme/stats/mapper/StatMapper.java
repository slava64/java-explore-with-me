package ru.practicum.explorewithme.stats.mapper;

import ru.practicum.explorewithme.stats.Stat;
import ru.practicum.explorewithme.stats.dto.EndpointHit;
import ru.practicum.explorewithme.stats.dto.NewEndpointHit;
import ru.practicum.explorewithme.stats.dto.ViewStats;

import java.util.ArrayList;
import java.util.List;

public class StatMapper {
    public static Stat toStat(NewEndpointHit newEndpointHit) {
        Stat stat = new Stat();
        stat.setApp(newEndpointHit.getApp());
        stat.setUri(newEndpointHit.getUri());
        stat.setIp(newEndpointHit.getIp());
        stat.setTimestamp(newEndpointHit.getTimestamp());
        return stat;
    }

    public static EndpointHit toEndpointHit(Stat stat) {
        return new EndpointHit(
                stat.getId(),
                stat.getApp(),
                stat.getUri(),
                stat.getIp(),
                stat.getTimestamp()
        );
    }

    public static ViewStats toViewStats(Object[] objects) {
        return new ViewStats(
                (String) objects[0],
                (String) objects[1],
                (Long) objects[2]
        );
    }

    public static List<ViewStats> toViewStats(List<Object[]> objectsList) {
        List<ViewStats> viewStatsList = new ArrayList<>();
        for (Object[] objects : objectsList) {
            viewStatsList.add(StatMapper.toViewStats(objects));
        }
        return viewStatsList;
    }
}
