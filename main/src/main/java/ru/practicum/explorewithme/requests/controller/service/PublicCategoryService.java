package ru.practicum.explorewithme.requests.controller.service;

import ru.practicum.explorewithme.categories.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getList(Integer from, Integer size);

    CategoryDto getItem(Long categoryId);
}
