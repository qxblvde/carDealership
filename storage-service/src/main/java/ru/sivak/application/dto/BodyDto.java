package ru.sivak.application.dto;


import ru.sivak.domain.valueObjects.BodyType;
import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;

import java.util.Set;

public record BodyDto(
        BodyType type,
        Money price,
        ComponentName modelName,
        Set<ModelName> suitableModels
) {}

