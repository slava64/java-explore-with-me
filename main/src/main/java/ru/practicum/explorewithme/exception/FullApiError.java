package ru.practicum.explorewithme.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
public class FullApiError extends ApiError {
    private List<String> errors;

    public FullApiError(HttpStatus status, String message, List<String> errors) {
        super(status, message);
        this.errors = errors;
    }

    public FullApiError(HttpStatus status, String message, String error) {
        super(status, message);
        errors = Arrays.asList(error);
    }
}
