package ru.practicum.explorewithme.categories.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.categories.dto.CategoryDto;
import ru.practicum.explorewithme.categories.dto.NewCategoryDto;
import ru.practicum.explorewithme.requests.controller.service.AdminCategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequestMapping(path = "/admin/categories")
@Slf4j
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {
    private final AdminCategoryService categoryService;

    @PostMapping
    public CategoryDto createItem(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.info("Create new category {}", newCategoryDto.toString());
        return categoryService.createItem(newCategoryDto);
    }

    @PatchMapping
    public CategoryDto updateItem(@RequestBody @Valid CategoryDto categoryDto) {
        log.info("Update category {}", categoryDto.toString());
        return categoryService.updateItem(categoryDto);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable("id") @Positive Long categoryId) {
        log.info("Delete category {}", categoryId);
        categoryService.deleteItem(categoryId);
    }
}
