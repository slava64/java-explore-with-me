package ru.practicum.explorewithme.events.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewModerationCommentDto {
    @NotBlank(message = "Описание не может быть пустой")
    @Size(min = 20, max = 7000, message = "Описание максимум 2000 символов")
    private String description;
}
