package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.dto.CarDto;
import ru.sivak.domain.entities.Car;

@Mapper(componentModel = "spring")
public interface CarMapper {
    @Mapping(target = "bodyType", source = "bodyType.bodyType")
    @Mapping(target = "brandName", source = "brandName.name")
    @Mapping(target = "color", source = "color.color")
    @Mapping(target = "driveType", source = "driveType.driveType")
    @Mapping(target = "enginePower", source = "engine.power")
    @Mapping(target = "engineVolume", source = "engine.volume")
    @Mapping(target = "fuelType", source = "fuel.fuelType")
    @Mapping(target = "modelName", source = "model.modelName")
    @Mapping(target = "transmissionType", source = "transmission.transmissionType")
    CarDto map(Car car);
}
