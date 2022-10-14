package ru.practicum.explorewithme.users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserShortDto implements Serializable {
    private Long id;
    private String email;
}
