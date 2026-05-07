package ru.sivak.integration.rest.dto;

import jakarta.validation.constraints.NotNull;
import ru.sivak.domain.entities.AssemblyOrder;

public record UpdateAssemblyOrderRequest(
        @NotNull AssemblyOrder.Status status
) {}
