package ru.sivak.integration.rest.mapper;

import org.mapstruct.Mapper;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.entities.Wheel;
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
import ru.sivak.integration.rest.dto.ComponentRequests;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ComponentRequestMapper {

    Set<ModelName> mapModelNames(Set<String> values);

    default Body toCreateBody(ComponentRequests.BodySaveRequest request) {
        return new Body(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                bodyType(request.bodyType())
        );
    }

    default Body toUpdateBody(UUID id, ComponentRequests.BodySaveRequest request) {
        return new Body(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                bodyType(request.bodyType())
        );
    }

    default Brand toCreateBrand(ComponentRequests.BrandSaveRequest request) {
        return new Brand(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                brandName(request.brandName())
        );
    }

    default Brand toUpdateBrand(UUID id, ComponentRequests.BrandSaveRequest request) {
        return new Brand(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                brandName(request.brandName())
        );
    }

    default Color toCreateColor(ComponentRequests.ColorSaveRequest request) {
        return new Color(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                colorValue(request.color())
        );
    }

    default Color toUpdateColor(UUID id, ComponentRequests.ColorSaveRequest request) {
        return new Color(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                colorValue(request.color())
        );
    }

    default Drive toCreateDrive(ComponentRequests.DriveSaveRequest request) {
        return new Drive(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                driveType(request.driveType())
        );
    }

    default Drive toUpdateDrive(UUID id, ComponentRequests.DriveSaveRequest request) {
        return new Drive(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                driveType(request.driveType())
        );
    }

    default Engine toCreateEngine(ComponentRequests.EngineSaveRequest request) {
        return new Engine(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                enginePower(request.power()),
                engineVolume(request.volume())
        );
    }

    default Engine toUpdateEngine(UUID id, ComponentRequests.EngineSaveRequest request) {
        return new Engine(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                enginePower(request.power()),
                engineVolume(request.volume())
        );
    }

    default Fuel toCreateFuel(ComponentRequests.FuelSaveRequest request) {
        return new Fuel(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                fuelType(request.fuelType())
        );
    }

    default Fuel toUpdateFuel(UUID id, ComponentRequests.FuelSaveRequest request) {
        return new Fuel(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                fuelType(request.fuelType())
        );
    }

    default Transmission toCreateTransmission(ComponentRequests.TransmissionSaveRequest request) {
        return new Transmission(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                transmissionType(request.transmissionType())
        );
    }

    default Transmission toUpdateTransmission(UUID id, ComponentRequests.TransmissionSaveRequest request) {
        return new Transmission(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels()),
                transmissionType(request.transmissionType())
        );
    }

    default Steering toCreateSteering(ComponentRequests.SteeringSaveRequest request) {
        return new Steering(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels())
        );
    }

    default Steering toUpdateSteering(UUID id, ComponentRequests.SteeringSaveRequest request) {
        return new Steering(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels())
        );
    }

    default Wheel toCreateWheel(ComponentRequests.WheelSaveRequest request) {
        return new Wheel(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels())
        );
    }

    default Wheel toUpdateWheel(UUID id, ComponentRequests.WheelSaveRequest request) {
        return new Wheel(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels())
        );
    }

    default Interior toCreateInterior(ComponentRequests.InteriorSaveRequest request) {
        return new Interior(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels())
        );
    }

    default Interior toUpdateInterior(UUID id, ComponentRequests.InteriorSaveRequest request) {
        return new Interior(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitableModels(request.suitableModels())
        );
    }

    default Model toCreateModel(ComponentRequests.ModelSaveRequest request) {
        ModelName modelName = modelName(request.modelName());
        Set<ModelName> suitable = suitableModels(request.suitableModels());
        if (suitable.isEmpty()) {
            suitable = Set.of(modelName);
        }
        return new Model(
                Id.newId(),
                money(request.price()),
                componentName(request.componentName()),
                suitable,
                modelName
        );
    }

    default Model toUpdateModel(UUID id, ComponentRequests.ModelSaveRequest request) {
        ModelName modelName = modelName(request.modelName());
        Set<ModelName> suitable = suitableModels(request.suitableModels());
        if (suitable.isEmpty()) {
            suitable = Set.of(modelName);
        }
        return new Model(
                Id.of(id),
                money(request.price()),
                componentName(request.componentName()),
                suitable,
                modelName
        );
    }

    default Set<ModelName> suitableModels(Set<String> values) {
        if (values == null || values.isEmpty()) {
            return Collections.emptySet();
        }
        return mapModelNames(values);
    }

    default BodyType bodyType(String value) {
        return BodyType.of(value);
    }

    default BrandName brandName(String value) {
        return BrandName.of(value);
    }

    default ColorValue colorValue(String value) {
        return ColorValue.of(value);
    }

    default DriveType driveType(String value) {
        return DriveType.of(value);
    }

    default FuelType fuelType(String value) {
        return FuelType.of(value);
    }

    default TransmissionType transmissionType(String value) {
        return TransmissionType.of(value);
    }

    default ModelName modelName(String value) {
        return ModelName.of(value);
    }

    default EnginePower enginePower(Integer value) {
        return EnginePower.of(value);
    }

    default EngineVolume engineVolume(Integer value) {
        return EngineVolume.of(value);
    }

    default Money money(BigDecimal value) {
        return Money.of(value);
    }

    default ComponentName componentName(String value) {
        return ComponentName.of(value);
    }
}
