package ru.practicum.explorewithme.events.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.explorewithme.locations.LocationDto;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewEventDto {
    @NotBlank(message = "Анотация не может быть пустой")
    @Size(min = 20, max = 2000, message = "Анотация максимум 2000 символов")
    private String annotation;
    @NotNull(message = "Категория не может быть пустой")
    @Positive
    private Long category;
    @NotBlank(message = "Описание не может быть пустой")
    @Size(min = 20, max = 7000, message = "Описание максимум 2000 символов")
    private String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;
    @NotNull
    private LocationDto location;
    @NotNull
    private Boolean paid = false;
    @PositiveOrZero
    private Integer participantLimit = 0;
    @NotNull
    private Boolean requestModeration = true;
    @NotBlank(message = "Загаловок не может быть пустым")
    @Size(min = 3, max = 120, message = "Длина загаловка от 3 до 120 символов")
    private String title;
}
