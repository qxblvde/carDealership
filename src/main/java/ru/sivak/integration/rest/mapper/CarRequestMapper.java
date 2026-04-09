package ru.sivak.integration.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.query.CarQuery;
import ru.sivak.domain.valueObjects.BodyType;
import ru.sivak.domain.valueObjects.BrandName;
import ru.sivak.domain.valueObjects.ColorValue;
import ru.sivak.domain.valueObjects.DriveType;
import ru.sivak.domain.valueObjects.EnginePower;
import ru.sivak.domain.valueObjects.EngineVolume;
import ru.sivak.domain.valueObjects.FuelType;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;
import ru.sivak.domain.valueObjects.TransmissionType;
import ru.sivak.integration.rest.dto.CreateCarRequest;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CarRequestMapper {

    @Mapping(target = "bodyId", source = "request.bodyId")
    @Mapping(target = "brandId", source = "request.brandId")
    @Mapping(target = "colorId", source = "request.colorId")
    @Mapping(target = "driveId", source = "request.driveId")
    @Mapping(target = "engineId", source = "request.engineId")
    @Mapping(target = "fuelId", source = "request.fuelId")
    @Mapping(target = "modelId", source = "request.modelId")
    @Mapping(target = "transmissionId", source = "request.transmissionId")
    @Mapping(target = "steeringId", source = "request.steeringId")
    @Mapping(target = "wheelId", source = "request.wheelId")
    @Mapping(target = "interiorId", source = "request.interiorId")
    CreateCar toCreateCar(CreateCarRequest request);

    CarQuery toQuery(
            String brandName,
            String modelName,
            String bodyType,
            String color,
            String driveType,
            Integer enginePower,
            Integer engineVolume,
            String fuelType,
            String transmissionType,
            BigDecimal minPrice,
            BigDecimal maxPrice
    );

    default Id toId(UUID value) {
        return value == null ? null : Id.of(value);
    }

    default BodyType mapBodyType(String value) {
        return value == null ? null : BodyType.of(value);
    }

    default BrandName mapBrandName(String value) {
        return value == null ? null : BrandName.of(value);
    }

    default ColorValue mapColorValue(String value) {
        return value == null ? null : ColorValue.of(value);
    }

    default DriveType mapDriveType(String value) {
        return value == null ? null : DriveType.of(value);
    }

    default FuelType mapFuelType(String value) {
        return value == null ? null : FuelType.of(value);
    }

    default TransmissionType mapTransmissionType(String value) {
        return value == null ? null : TransmissionType.of(value);
    }

    default ModelName mapModelName(String value) {
        return value == null ? null : ModelName.of(value);
    }

    default EnginePower mapEnginePower(Integer value) {
        return value == null ? null : EnginePower.of(value);
    }

    default EngineVolume mapEngineVolume(Integer value) {
        return value == null ? null : EngineVolume.of(value);
    }

    default Money mapMoney(BigDecimal value) {
        return value == null ? null : Money.of(value);
    }

    record CreateCar(
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
    ) {
    }
}
