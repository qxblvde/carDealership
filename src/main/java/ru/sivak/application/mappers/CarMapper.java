package ru.sivak.application.mappers;

import ru.sivak.application.dto.CarDto;
import ru.sivak.domain.entities.Car;

public final class CarMapper {
    private CarMapper() {}

    public static CarDto toDto(Car car) {
        return new CarDto(
                car.getId(),
                car.getBodyType().getBodyType(),
                car.getBrandName().getName(),
                car.getColor().getColor(),
                car.getDriveType().getDriveType(),
                car.getEngine().getPower(),
                car.getEngine().getVolume(),
                car.getFuel().getFuelType(),
                car.getModel().getModelName(),
                car.getPrice(),
                car.getTransmission().getTransmissionType()
        );
    }
}
