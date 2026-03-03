package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.*;

import java.util.Set;

public record ComponentDto(
        Id id,
        ComponentName name,
        ComponentType type,
        Money price,
        Set<ModelName> suitableModels
) {}
