package ru.practicum.explorewithme.compilations.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.compilations.Compilation;
import ru.practicum.explorewithme.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.compilations.dto.NewCompilationDto;
import ru.practicum.explorewithme.events.Event;
import ru.practicum.explorewithme.events.mapper.EventMapper;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompilationMapper {
    public static CompilationDto toCompilationDto(Compilation compilation) {
        return new CompilationDto(
                compilation.getEvents() != null
                        ? EventMapper.toEventShortDto(compilation.getEvents())
                        : new ArrayList<>(),
                compilation.getId(),
                compilation.getPinned(),
                compilation.getName()
        );
    }

    public static List<CompilationDto> toCompilationDto(List<Compilation> compilationList) {
        List<CompilationDto> compilationDtoList = new ArrayList<>();
        for (Compilation compilation : compilationList) {
            compilationDtoList.add(CompilationMapper.toCompilationDto(compilation));
        }
        return compilationDtoList;
    }

    public static Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> eventList) {
        Compilation compilation = new Compilation();
        compilation.setName(newCompilationDto.getTitle());
        compilation.setPinned(newCompilationDto.getPinned());
        if (!eventList.isEmpty()) {
            compilation.setEvents(eventList);
        }
        return compilation;
    }
}
