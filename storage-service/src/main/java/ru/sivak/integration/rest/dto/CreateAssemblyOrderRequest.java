package ru.sivak.integration.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record CreateAssemblyOrderRequest(
        @NotNull UUID sourceOrderId,
        @NotBlank String sourceOrderType,
        UUID carId,
        UUID warehouseEmployeeId,
        Set<UUID> requiredComponentIds
) {
    public CreateAssemblyOrderRequest {
        if (requiredComponentIds == null) {
            requiredComponentIds = new HashSet<>();
        }
    }
}
