package de.jandev.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends Exception {

    private final HttpStatus httpStatus;

    public ApplicationException(HttpStatus status, String message) {
        this(status, message, null);
    }

    public ApplicationException(HttpStatus status, String message, Throwable throwable) {
        super(message, throwable);
        httpStatus = status;
    }
}
