package ru.practicum.explorewithme.locations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto implements Serializable {
    @Positive
    private Float lat;
    @Positive
    private Float lon;
}
