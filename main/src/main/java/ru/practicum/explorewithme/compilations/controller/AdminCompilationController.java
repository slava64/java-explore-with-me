package ru.practicum.explorewithme.compilations.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.compilations.dto.CompilationDto;
import ru.practicum.explorewithme.compilations.dto.NewCompilationDto;
import ru.practicum.explorewithme.compilations.service.AdminCompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/compilations")
@Slf4j
@RequiredArgsConstructor
@Validated
public class AdminCompilationController {
    private final AdminCompilationService compilationService;

    @PostMapping
    public CompilationDto createItem(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("Create new compilation {}", newCompilationDto.toString());
        return compilationService.createItem(newCompilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteItem(@PathVariable("compId") Long compId) {
        log.info("Delete compilation {}", compId);
        compilationService.deleteItem(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEvent(
            @Positive @PathVariable("compId") Long compId,
            @Positive @PathVariable("eventId") Long eventId
    ) {
        log.info("Delete event {} from compilation {}", eventId, compId);
        compilationService.deleteEvent(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEvent(@PathVariable("compId") Long compId, @PathVariable("eventId") Long eventId) {
        log.info("Add event {} to compilation {}", eventId, compId);
        compilationService.addEvent(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void deleteItemFromMainPage(@Positive @PathVariable("compId") Long compId) {
        log.info("Delete compilation {} from main page", compId);
        compilationService.deleteItemFromMainPage(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void addItemToMainPage(@Positive @PathVariable("compId") Long compId) {
        log.info("Add compilation {} to main page", compId);
        compilationService.addItemToMainPage(compId);
    }
}
