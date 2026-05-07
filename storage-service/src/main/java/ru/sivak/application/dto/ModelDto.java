package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;

public record ModelDto(
         ModelName modelName,
         Money price,
         ComponentName componentName
) {
}
