package ru.sivak.application.mappers;

import ru.sivak.application.dto.SteeringDto;
import ru.sivak.domain.entities.Steering;

public class SteeringMapper {

    private SteeringMapper() {}

    public static SteeringDto toDto(Steering steering) {
        return new SteeringDto(
                steering.getPrice(),
                steering.getComponentName(),
                steering.getSuitableModels()
        );
    }
}