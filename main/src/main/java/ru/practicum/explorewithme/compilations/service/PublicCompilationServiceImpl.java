package ru.practicum.explorewithme.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.compilations.Compilation;
import ru.practicum.explorewithme.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.compilations.mapper.CompilationMapper;
import ru.practicum.explorewithme.compilations.repository.CompilationRepository;
import ru.practicum.explorewithme.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCompilationServiceImpl implements PublicCompilationService{
    private final CompilationRepository compilationRepository;

    @Override
    public List<CompilationDto> getList(Integer from, Integer size, Boolean pinned) {
        List<Compilation> compilationList = compilationRepository.getList(from, size, pinned);
        return CompilationMapper.toCompilationDto(compilationList);
    }

    @Override
    public CompilationDto getItem(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка событий не найдена")
        );
        return CompilationMapper.toCompilationDto(compilation);
    }
}
