package ru.practicum.explorewithme.categories.service;

import ru.practicum.explorewithme.categories.dto.CategoryDto;
import ru.practicum.explorewithme.categories.dto.NewCategoryDto;

public interface AdminCategoryService {
    CategoryDto createItem(NewCategoryDto newCategoryDto);

    CategoryDto updateItem(CategoryDto categoryDto);

    void deleteItem(Long categoryId);
}
