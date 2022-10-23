package ru.practicum.explorewithme.compilations.service;

import ru.practicum.explorewithme.compilations.dto.CompilationDto;

import java.util.List;

public interface PublicCompilationService {
    List<CompilationDto> getList(Integer from, Integer size, Boolean pinned);

    CompilationDto getItem(Long compId);
}
