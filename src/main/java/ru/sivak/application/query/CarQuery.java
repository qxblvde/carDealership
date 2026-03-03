package ru.sivak.application.query;

import lombok.Builder;
import lombok.Getter;
import ru.sivak.domain.valueObjects.*;

@Getter
@Builder
public class CarQuery {
    private final BrandName brandName;
    private final ModelName modelName;
    private final BodyType bodyType;
    private final Color color;
    private final DriveType driveType;
    private final EnginePower enginePower;
    private final EngineVolume engineVolume;
    private final FuelType fuelType;
    private final TransmissionType transmissionType;
    private final Money minPrice;
    private final Money maxPrice;
}
