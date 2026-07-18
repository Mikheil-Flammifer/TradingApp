package com.my.tradingapp.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.Map;

// Returned by @ControllerAdvice when validation or business errors occur
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(

        int status,
        String error,
        String message,

        // field-level validation errors e.g. {"email": "must be valid", "password": "too short"}
        // null when there are no field errors
        Map<String, String> fieldErrors,

        Instant timestamp

) {
    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(status, error, message, null, Instant.now());
    }

    public static ErrorResponse withFieldErrors(int status, String error, String message, Map<String, String> fieldErrors) {
        return new ErrorResponse(status, error, message, fieldErrors, Instant.now());
    }
}