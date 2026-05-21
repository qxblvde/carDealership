package ru.sivak.application.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AvailableCarDto(
        UUID id,
        String bodyType,
        String brandName,
        String color,
        String driveType,
        Integer enginePower,
        Integer engineVolume,
        String fuelType,
        String modelName,
        BigDecimal price,
        String transmissionType,
        Integer availableQuantity
) {
}
