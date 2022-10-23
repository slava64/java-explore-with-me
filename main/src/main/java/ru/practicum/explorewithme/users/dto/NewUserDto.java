package ru.practicum.explorewithme.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewUserDto {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 50, message = "Имя больше 50 символов")
    private String name;
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Не верный формат email")
    private String email;
}
