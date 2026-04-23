package ru.sivak.integration.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateTestDriveRequest(
        @NotNull UUID carId,
        @NotNull LocalDate scheduledTime
) {
}
