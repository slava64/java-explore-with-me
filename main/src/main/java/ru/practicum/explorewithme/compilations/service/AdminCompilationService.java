package ru.practicum.explorewithme.compilations.service;

import ru.practicum.explorewithme.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.compilations.dto.NewCompilationDto;

public interface AdminCompilationService {
    CompilationDto createItem(NewCompilationDto newCompilationDto);

    void deleteItem(Long compId);

    void deleteEvent(Long compId, Long eventId);

    void addEvent(Long compId, Long eventId);

    void deleteItemFromMainPage(Long compId);

    void addItemToMainPage(Long compId);
}
