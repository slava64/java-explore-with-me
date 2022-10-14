package ru.practicum.explorewithme.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.compilations.service.PublicCompilationService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PublicCompilationController {
    private final PublicCompilationService compilationService;

    @GetMapping
    public List<CompilationDto> getList(
            @RequestParam(value = "pinned", required = false) Boolean pinned,
            @PositiveOrZero @RequestParam(value = "from", defaultValue = "0") Integer from,
            @Positive @RequestParam(value = "size", defaultValue = "10") Integer size
    ) {
        log.info("Find compilations from {} size {} pinned: {}", from, size, pinned);
        return compilationService.getList(from, size, pinned);
    }

    @GetMapping("/{compId}")
    public CompilationDto getItem(@Positive @PathVariable("compId") Long compId) {
        log.info("Find compilation {}", compId);
        return compilationService.getItem(compId);
    }
}
