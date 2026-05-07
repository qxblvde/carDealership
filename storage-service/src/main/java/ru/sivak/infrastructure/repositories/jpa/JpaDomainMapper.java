package ru.sivak.infrastructure.repositories.jpa;

import ru.sivak.domain.entities.*;
import ru.sivak.domain.valueObjects.*;
import ru.sivak.infrastructure.persistence.entity.*;

import java.util.Set;
import java.util.stream.Collectors;

public final class JpaDomainMapper {
    private JpaDomainMapper() {
    }

    public static Body toDomain(BodyEntity entity) {
        return new Body(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels()),
                BodyType.of(entity.getBodyType())
        );
    }

    public static Brand toDomain(BrandEntity entity) {
        return new Brand(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels()),
                BrandName.of(entity.getBrandName())
        );
    }

    public static Color toDomain(ColorEntity entity) {
        return new Color(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels()),
                ColorValue.of(entity.getColorValue())
        );
    }

    public static Drive toDomain(DriveEntity entity) {
        return new Drive(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels()),
                DriveType.of(entity.getDriveType())
        );
    }

    public static Engine toDomain(EngineEntity entity) {
        return new Engine(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels()),
                EnginePower.of(entity.getEnginePower()),
                EngineVolume.of(entity.getEngineVolume())
        );
    }

    public static Fuel toDomain(FuelEntity entity) {
        return new Fuel(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels()),
                FuelType.of(entity.getFuelType())
        );
    }

    public static Transmission toDomain(TransmissionEntity entity) {
        return new Transmission(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels()),
                TransmissionType.of(entity.getTransmissionType())
        );
    }

    public static Steering toDomain(SteeringEntity entity) {
        return new Steering(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels())
        );
    }

    public static Wheel toDomain(WheelEntity entity) {
        return new Wheel(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels())
        );
    }

    public static Interior toDomain(InteriorEntity entity) {
        return new Interior(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                toModelNames(entity.getSuitableModels())
        );
    }

    public static Model toDomain(CarModelEntity entity) {
        ModelName modelName = ModelName.of(entity.getModelName());
        return new Model(
                Id.of(entity.getId()),
                Money.of(entity.getPrice()),
                ComponentName.of(entity.getComponentName()),
                Set.of(modelName),
                modelName
        );
    }

    public static Car toDomain(CarEntity entity) {
        return Car.builder()
                .id(Id.of(entity.getId()))
                .bodyType(toDomain(entity.getBody()))
                .brandName(toDomain(entity.getBrand()))
                .color(toDomain(entity.getColor()))
                .driveType(toDomain(entity.getDrive()))
                .engine(toDomain(entity.getEngine()))
                .fuel(toDomain(entity.getFuel()))
                .model(toDomain(entity.getModel()))
                .price(Money.of(entity.getPrice()))
                .transmission(toDomain(entity.getTransmission()))
                .steering(toDomain(entity.getSteering()))
                .wheel(toDomain(entity.getWheel()))
                .interior(toDomain(entity.getInterior()))
                .build();
    }

    public static void mapToEntity(Body source, BodyEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setBodyType(source.getBodyType().getType());
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Brand source, BrandEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setBrandName(source.getName().getName());
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Color source, ColorEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setColorValue(source.getColor().getColor());
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Drive source, DriveEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setDriveType(source.getDriveType().getType());
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Engine source, EngineEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setEnginePower(source.getPower().getPower());
        target.setEngineVolume(source.getVolume().getVolume());
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Fuel source, FuelEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setFuelType(source.getFuelType().getType());
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Transmission source, TransmissionEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setTransmissionType(source.getTransmissionType().getType());
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Steering source, SteeringEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Wheel source, WheelEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Interior source, InteriorEntity target, Set<CarModelEntity> suitableModels) {
        mapComponent(source, target);
        target.setSuitableModels(suitableModels);
    }

    public static void mapToEntity(Model source, CarModelEntity target) {
        target.setId(source.getId().getId());
        target.setPrice(source.getPrice().getAmount());
        target.setComponentName(source.getComponentName().getName());
        target.setModelName(source.getModelName().getName());
    }

    public static void mapToEntity(
            Car source,
            CarEntity target,
            BodyEntity body,
            BrandEntity brand,
            ColorEntity color,
            DriveEntity drive,
            EngineEntity engine,
            FuelEntity fuel,
            CarModelEntity model,
            TransmissionEntity transmission,
            SteeringEntity steering,
            WheelEntity wheel,
            InteriorEntity interior
    ) {
        target.setId(source.getId().getId());
        target.setBody(body);
        target.setBrand(brand);
        target.setColor(color);
        target.setDrive(drive);
        target.setEngine(engine);
        target.setFuel(fuel);
        target.setModel(model);
        target.setTransmission(transmission);
        target.setSteering(steering);
        target.setWheel(wheel);
        target.setInterior(interior);
        target.setPrice(source.getPrice().getAmount());
    }

    private static Set<ModelName> toModelNames(Set<CarModelEntity> entities) {
        return entities.stream()
                .map(CarModelEntity::getModelName)
                .map(ModelName::of)
                .collect(Collectors.toSet());
    }

    private static void mapComponent(Component source, AbstractComponentEntity target) {
        target.setId(source.getId().getId());
        target.setPrice(source.getPrice().getAmount());
        target.setComponentName(source.getComponentName().getName());
    }
}
