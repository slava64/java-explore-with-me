package ru.practicum.explorewithme.categories.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.explorewithme.categories.Category;
import ru.practicum.explorewithme.categories.dto.CategoryDto;
import ru.practicum.explorewithme.categories.dto.NewCategoryDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public static List<CategoryDto> toCategoryDto(List<Category> categoryList) {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDtoList.add(CategoryMapper.toCategoryDto(category));
        }
        return categoryDtoList;
    }

    public static Category toNewCategory(NewCategoryDto newCategoryDto) {
        Category category = new Category();
        category.setName(newCategoryDto.getName());
        return category;
    }

    public static Category toCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }
}
