package ru.sivak.integration.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateOrderRequest(
        @NotNull UUID managerId,
        @NotNull UUID clientId,
        @NotNull UUID carId
) {
}
