package ru.practicum.explorewithme.stats.repository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepositoryCustom {
    List<Object[]> getList(LocalDateTime start, LocalDateTime end, List<String> uris, Boolean unique);
}
