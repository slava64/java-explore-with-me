package ru.practicum.explorewithme.compilations.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {
    private List<@Positive Long> events;
    private Boolean pinned = false;
    @NotBlank(message = "Заголовок не может быть пустым")
    @Size(min = 3, max = 255, message = "Заголовок минимум 3, максимум 255 символов")
    private String title;
}
