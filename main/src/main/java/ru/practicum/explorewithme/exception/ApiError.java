package ru.practicum.explorewithme.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ApiError {
    private HttpStatus status;
    private String message;
    private String timestamp;
    private String reason;

    public ApiError(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
        DateTimeFormatter dateTimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        timestamp = dateTimeformatter.format(LocalDateTime.now());
        reason = this.status.getReasonPhrase();
    }
}
