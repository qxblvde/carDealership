package ru.sivak.application.dto;

import ru.sivak.domain.valueObjects.*;

public record CarDto(
        Id id,
        BodyType bodyType,
        BrandName brandName,
        Color color,
        DriveType driveType,
        EnginePower enginePower,
        EngineVolume engineVolume,
        FuelType fuelType,
        ModelName modelName,
        Money price,
        TransmissionType transmissionType
) {}
