package ru.sivak.application.services;

import ru.sivak.application.dto.CarDto;
import ru.sivak.domain.valueObjects.Id;

public interface ICarConfiguratorService {
    CarDto createFromComponents(
            Id bodyId,
            Id brandId,
            Id colorId,
            Id driveId,
            Id engineId,
            Id fuelId,
            Id modelId,
            Id transmissionId,
            Id steeringId,
            Id wheelId,
            Id interiorId
    );
}
