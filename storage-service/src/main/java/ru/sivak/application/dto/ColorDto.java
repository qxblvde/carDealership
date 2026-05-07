package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.ColorValue;
import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;

import java.util.Set;

public record ColorDto(
        ColorValue color,
        Money price,
        ComponentName componentName,
        Set<ModelName> suitableModels
) {
}
