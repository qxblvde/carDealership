package ru.sivak.application.mappers;

import ru.sivak.application.dto.WheelDto;
import ru.sivak.domain.entities.Wheel;

public class WheelMapper {
    private WheelMapper() {}

    public static WheelDto toDto(Wheel wheel) {
        return new WheelDto(
                wheel.getPrice(),
                wheel.getComponentName(),
                wheel.getSuitableModels()
        );
    }
}
