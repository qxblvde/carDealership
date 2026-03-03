package ru.sivak.application.mappers;

import ru.sivak.application.dto.CarDto;
import ru.sivak.domain.entities.Car;

public final class CarMapper {
    private CarMapper() {}

    public static CarDto toDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getBodyType(),
                car.getBrandName(),
                car.getColor(),
                car.getDriveType(),
                car.getEnginePower(),
                car.getEngineVolume(),
                car.getFuelType(),
                car.getModelName(),
                car.getPrice(),
                car.getTransmissionType()
        );
    }
}
