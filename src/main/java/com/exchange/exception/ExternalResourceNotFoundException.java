package com.exchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExternalResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 3230362952233846477L;

    public ExternalResourceNotFoundException(String message) {
        super(message);
    }
}
