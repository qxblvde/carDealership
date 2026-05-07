package ru.sivak.application.dto;

import ru.sivak.domain.entities.AssemblyOrder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record AssemblyOrderDto(
        UUID id,
        UUID sourceOrderId,
        String sourceOrderType,
        UUID carId,
        UUID warehouseEmployeeId,
        Set<UUID> requiredComponentIds,
        AssemblyOrder.Status status,
        Instant createdAt,
        Instant updatedAt
) {}
