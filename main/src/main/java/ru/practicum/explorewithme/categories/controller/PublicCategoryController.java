package ru.practicum.explorewithme.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.categories.dto.CategoryDto;
import ru.practicum.explorewithme.requests.controller.service.PublicCategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PublicCategoryController {
    private final PublicCategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getList(
            @RequestParam(value = "from", defaultValue = "0") @PositiveOrZero Integer from,
            @RequestParam(value = "size", defaultValue = "10") @Positive Integer size
    ) {
        log.info("Find categories from {} size {}", from, size);
        return categoryService.getList(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getItem(@PathVariable("catId") @Positive Long catId) {
        log.info("Find category {}", catId);
        return categoryService.getItem(catId);
    }
}
