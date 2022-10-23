package ru.practicum.explorewithme.events;

import java.util.Optional;

public enum Sort {
    // По дате
    EVENT_DATE,
    // По кол-ву просмотров
    VIEWS;

    public static Optional<Sort> from(String stringSort) {
        for (Sort sort : values()) {
            if (sort.name().equalsIgnoreCase(stringSort)) {
                return Optional.of(sort);
            }
        }
        return Optional.empty();
    }
}
