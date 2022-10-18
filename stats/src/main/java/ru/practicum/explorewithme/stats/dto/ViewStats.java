package ru.practicum.explorewithme.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewStats implements Serializable {
    public String app;
    public String uri;
    public Long hits;
}
