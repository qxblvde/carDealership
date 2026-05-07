package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.DriveType;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;

import java.util.Set;

public record DriveDto (
        DriveType driveType,
        Money price,
        ComponentName componentName,
        Set<ModelName> suitableModels

) {
}
