package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.FuelType;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;

import java.util.Set;

public record FuelDto(
     FuelType fuelType,
     Money price,
     ComponentName componentName,
     Set<ModelName> suitableModels
) {}
