package ru.practicum.explorewithme.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.explorewithme.events.dto.EventShortDto;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompilationDto implements Serializable {
    private List<EventShortDto> events;
    private Long id;
    private Boolean pinned;
    private String title;
}
