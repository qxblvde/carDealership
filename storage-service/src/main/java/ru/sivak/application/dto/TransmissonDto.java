package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;
import ru.sivak.domain.valueObjects.TransmissionType;

import java.util.Set;


public record TransmissonDto(
         TransmissionType type,
         Money price,
         ComponentName componentName,
         Set<ModelName> suitableModels
) {
}
