package ru.sivak.application.mappers;

import ru.sivak.application.dto.FuelDto;
import ru.sivak.domain.entities.Fuel;

public class FuelMapper {
    private FuelMapper() {}

    public static FuelDto toDto(Fuel fuel) {
        return new FuelDto(
                fuel.getFuelType(),
                fuel.getPrice(),
                fuel.getComponentName(),
                fuel.getSuitableModels()
        );
    }
}
