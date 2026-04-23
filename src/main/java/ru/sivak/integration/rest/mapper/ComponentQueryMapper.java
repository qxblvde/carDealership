package ru.sivak.integration.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import ru.sivak.application.query.BodyQuery;
import ru.sivak.application.query.BrandQuery;
import ru.sivak.application.query.ColorQuery;
import ru.sivak.application.query.DriveQuery;
import ru.sivak.application.query.EngineQuery;
import ru.sivak.application.query.FuelQuery;
import ru.sivak.application.query.InteriorQuery;
import ru.sivak.application.query.ModelQuery;
import ru.sivak.application.query.SteeringQuery;
import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.domain.valueObjects.BodyType;
import ru.sivak.domain.valueObjects.BrandName;
import ru.sivak.domain.valueObjects.ColorValue;
import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.DriveType;
import ru.sivak.domain.valueObjects.EnginePower;
import ru.sivak.domain.valueObjects.EngineVolume;
import ru.sivak.domain.valueObjects.FuelType;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;
import ru.sivak.domain.valueObjects.TransmissionType;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring", nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface ComponentQueryMapper {

    BodyQuery toBodyQuery(String type, String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    BrandQuery toBrandQuery(String brandName, String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    ColorQuery toColorQuery(String color, String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    DriveQuery toDriveQuery(String driveType, String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    EngineQuery toEngineQuery(Integer power, Integer volume, String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    FuelQuery toFuelQuery(String fuelType, String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    TransmissionQuery toTransmissionQuery(String type, String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    SteeringQuery toSteeringQuery(String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    WheelQuery toWheelQuery(String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    InteriorQuery toInteriorQuery(String componentName, String modelName, BigDecimal minPrice, BigDecimal maxPrice);

    ModelQuery toModelQuery(String modelName, String componentName, BigDecimal minPrice, BigDecimal maxPrice);

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

    default ComponentName mapComponentName(String value) {
        return value == null ? null : ComponentName.of(value);
    }
}
