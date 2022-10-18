package ru.practicum.explorewithme.compilations.repository;

import ru.practicum.explorewithme.compilations.Compilation;

import java.util.List;

public interface CompilationRepositoryCustom {
    List<Compilation> getList(Integer from, Integer size, Boolean pinned);
}
