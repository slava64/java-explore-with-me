package ru.practicum.explorewithme.requests.controller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.categories.Category;
import ru.practicum.explorewithme.categories.dto.CategoryDto;
import ru.practicum.explorewithme.categories.mapper.CategoryMapper;
import ru.practicum.explorewithme.categories.repository.CategoryRepository;
import ru.practicum.explorewithme.exception.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getList(Integer from, Integer size) {
        Pageable pageable = PageRequest.of((int) from / size, size);
        List<Category> categoryList = categoryRepository.findAll(pageable).getContent();
        return CategoryMapper.toCategoryDto(categoryList);
    }

    @Override
    public CategoryDto getItem(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Категория не найдена")
        );
        return CategoryMapper.toCategoryDto(category);
    }
}
