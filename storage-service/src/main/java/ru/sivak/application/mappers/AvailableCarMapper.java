package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.entities.CarStock;

@Mapper(componentModel = "spring")
public interface AvailableCarMapper {

    default AvailableCarDto map(Car car, CarStock stock) {
        return new AvailableCarDto(
                car.getId().getId(),
                car.getBodyType().getBodyType().getType(),
                car.getBrandName().getName().getName(),
                car.getColor().getColor().getColor(),
                car.getDriveType().getDriveType().getType(),
                car.getEngine().getPower().getPower(),
                car.getEngine().getVolume().getVolume(),
                car.getFuel().getFuelType().getType(),
                car.getModel().getModelName().getName(),
                car.getPrice().getAmount(),
                car.getTransmission().getTransmissionType().getType(),
                stock.getQuantity() - stock.getReserved()
        );
    }
}
