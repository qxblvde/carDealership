package ru.sivak.integration.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateCarRequest(
        @NotNull UUID bodyId,
        @NotNull UUID brandId,
        @NotNull UUID colorId,
        @NotNull UUID driveId,
        @NotNull UUID engineId,
        @NotNull UUID fuelId,
        @NotNull UUID modelId,
        @NotNull UUID transmissionId,
        @NotNull UUID steeringId,
        @NotNull UUID wheelId,
        @NotNull UUID interiorId
) {
}
