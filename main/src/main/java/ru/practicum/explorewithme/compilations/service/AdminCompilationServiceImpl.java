package ru.practicum.explorewithme.compilations.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.compilations.Compilation;
import ru.practicum.explorewithme.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.compilations.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilations.mapper.CompilationMapper;
import ru.practicum.explorewithme.compilations.repository.CompilationRepository;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.repository.EventRepository;
import ru.practicum.explorewithme.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdminCompilationServiceImpl implements AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDto createItem(NewCompilationDto newCompilationDto) {
        List<Event> eventList = new ArrayList<>();
        if (newCompilationDto.getEvents() != null) {
            for (Long eventId : newCompilationDto.getEvents()) {
                Event event = eventRepository.findById(eventId).orElseThrow(
                        () -> new NotFoundException(String.format("Событие %d не найдено", eventId))
                );
                eventList.add(event);
            }
        }
        Compilation compilation = CompilationMapper.toCompilation(newCompilationDto, eventList);
        Compilation savedCompilation = compilationRepository.save(compilation);
        return CompilationMapper.toCompilationDto(savedCompilation);
    }

    @Override
    public void deleteItem(Long compId) {
        compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка событий не найдена")
        );
        compilationRepository.deleteById(compId);
    }

    @Override
    public void deleteEvent(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка событий не найдена")
        );
        compilation.getEvents().removeIf(event -> Objects.equals(event.getId(), eventId));
        compilationRepository.save(compilation);
    }

    @Override
    public void addEvent(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка событий не найдена")
        );
        Event event = eventRepository.findById(eventId).orElseThrow(
                () -> new NotFoundException(String.format("Событие %d не найдено", eventId))
        );
        for (Event element : compilation.getEvents()) {
            if (element.getId().equals(event.getId())) {
                return;
            }
        }
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
    }

    @Override
    public void deleteItemFromMainPage(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка событий не найдена")
        );
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void addItemToMainPage(Long compId) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(
                () -> new NotFoundException("Подборка событий не найдена")
        );
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}
