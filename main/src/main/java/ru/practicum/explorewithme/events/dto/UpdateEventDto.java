package ru.practicum.explorewithme.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEventDto {
    @Size(min = 20, max = 2000, message = "Анотация максимум 2000 символов")
    private String annotation;
    @Positive
    private Long category;
    @Size(min = 20, max = 7000, message = "Описание максимум 2000 символов")
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull(message = "Event Id не может быть пустым")
    @Positive
    private Long eventId;
    private Boolean paid;
    @PositiveOrZero
    private Integer participantLimit;
    @Size(min = 3, max = 120, message = "Длина загаловка от 3 до 120 символов")
    private String title;
}
