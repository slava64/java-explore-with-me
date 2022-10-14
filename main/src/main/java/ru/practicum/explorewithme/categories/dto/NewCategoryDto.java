package ru.practicum.explorewithme.categories.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    @NotBlank(message = "Категория не может быть пустой")
    @Size(max = 50, message = "Категория больше 50 символов")
    private String name;
}
