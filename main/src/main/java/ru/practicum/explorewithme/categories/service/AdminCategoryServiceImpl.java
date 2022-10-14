package ru.practicum.explorewithme.categories.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.explorewithme.categories.Category;
import ru.practicum.explorewithme.categories.dto.CategoryDto;
import ru.practicum.explorewithme.categories.dto.NewCategoryDto;
import ru.practicum.explorewithme.categories.mapper.CategoryMapper;
import ru.practicum.explorewithme.categories.repository.CategoryRepository;
import ru.practicum.explorewithme.exception.NotFoundException;

@Service
@RequiredArgsConstructor
public class AdminCategoryServiceImpl implements AdminCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createItem(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.toNewCategory(newCategoryDto);
        Category newCategory = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(newCategory);
    }

    @Override
    public CategoryDto updateItem(CategoryDto categoryDto) {
        categoryRepository.findById(categoryDto.getId())
                .orElseThrow(() -> new NotFoundException("Категория не найдена"));
        Category category = CategoryMapper.toCategory(categoryDto);
        Category updateCategory = categoryRepository.save(category);
        return CategoryMapper.toCategoryDto(updateCategory);
    }

    @Override
    public void deleteItem(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NotFoundException("Категория не найдена"));
        categoryRepository.delete(category);
    }
}
