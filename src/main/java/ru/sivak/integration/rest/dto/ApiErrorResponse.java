package ru.sivak.integration.rest.dto;

import java.time.Instant;

public record ApiErrorResponse(
        String message,
        int status,
        Instant timestamp
) {
}

