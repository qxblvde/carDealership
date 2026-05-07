package ru.sivak.integration.rest.dto;

import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Set;

public final class ComponentRequests {
    private ComponentRequests() {
    }

    public record BodySaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels,
            @NonNull String bodyType
    ) {
    }

    public record BrandSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels,
            @NonNull String brandName
    ) {
    }

    public record ColorSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels,
            @NonNull String color
    ) {
    }

    public record DriveSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels,
            @NonNull String driveType
    ) {
    }

    public record EngineSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels,
            @NonNull Integer power,
            @NonNull Integer volume
    ) {
    }

    public record FuelSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels,
            @NonNull String fuelType
    ) {
    }

    public record TransmissionSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels,
            @NonNull String transmissionType
    ) {
    }

    public record SteeringSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels
    ) {
    }

    public record WheelSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels
    ) {
    }

    public record InteriorSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels
    ) {
    }

    public record ModelSaveRequest(
            @NonNull BigDecimal price,
            @NonNull String componentName,
            @NonNull Set<String> suitableModels,
            @NonNull String modelName
    ) {
    }
}
