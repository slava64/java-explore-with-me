package ru.practicum.explorewithme.events;

import java.util.Optional;

public enum State {
    // Ожидает
    PENDING,
    // Опубликовано
    PUBLISHED,
    // Отменено
    CANCELED;

    public static Optional<State> from(String stringState) {
        for (State state : values()) {
            if (state.name().equalsIgnoreCase(stringState)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }
}
