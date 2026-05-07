package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.*;

import java.util.Set;

public record EngineDto(
        EnginePower power,
        EngineVolume volume,
        Money price,
        ComponentName componentName,
        Set<ModelName> suitableModels
) {
}
