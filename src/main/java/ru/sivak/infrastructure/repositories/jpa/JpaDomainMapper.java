package ru.sivak.infrastructure.repositories.jpa;

import ru.sivak.domain.entities.*;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.order.custom.CustomOrderState;
import ru.sivak.domain.order.BaseOrder;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.order.inStock.InStockOrderState;
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

    public static CustomOrder toDomain(CustomOrderEntity entity) {
        CustomOrder order = new CustomOrder(
                Id.of(entity.getId()),
                Id.of(entity.getManager().getId()),
                Id.of(entity.getClient().getId()),
                toDomain(entity.getCar())
        );
        applyCustomState(order, entity.getState());
        return order;
    }

    public static InStockOrder toDomain(InStockOrderEntity entity) {
        InStockOrder order = new InStockOrder(
                Id.of(entity.getId()),
                Id.of(entity.getManager().getId()),
                Id.of(entity.getClient().getId()),
                toDomain(entity.getCar())
        );
        applyInStockState(order, entity.getState());
        return order;
    }

    public static TestDriveRequest toDomain(TestDriveRequestEntity entity) {
        return TestDriveRequest.builder()
                .id(Id.of(entity.getId()))
                .clientId(Id.of(entity.getClient().getId()))
                .carId(Id.of(entity.getCar().getId()))
                .scheduledTime(entity.getScheduledTime())
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

    public static void mapToEntity(
            CustomOrder source,
            CustomOrderEntity target,
            AppUserEntity manager,
            AppUserEntity client,
            CarEntity car
    ) {
        mapOrder(source, target, manager, client, car, customStateCode(source.getStateType()));
    }

    public static void mapToEntity(
            InStockOrder source,
            InStockOrderEntity target,
            AppUserEntity manager,
            AppUserEntity client,
            CarEntity car
    ) {
        mapOrder(source, target, manager, client, car, inStockStateCode(source.getStateType()));
    }

    public static void mapToEntity(
            TestDriveRequest source,
            TestDriveRequestEntity target,
            AppUserEntity client,
            CarEntity car
    ) {
        target.setId(source.getId().getId());
        target.setClient(client);
        target.setCar(car);
        target.setScheduledTime(source.getScheduledTime());
    }

    public static String customStateCode(Class<? extends CustomOrderState> type) {
        if (type == ru.sivak.domain.order.custom.CreatedState.class) {
            return "CREATED";
        }
        if (type == ru.sivak.domain.order.custom.ApprovedState.class) {
            return "APPROVED";
        }
        if (type == ru.sivak.domain.order.custom.WaitingPaymentState.class) {
            return "WAITING_PAYMENT";
        }
        if (type == ru.sivak.domain.order.custom.PaidState.class) {
            return "PAID";
        }
        if (type == ru.sivak.domain.order.custom.WaitingDeliveryState.class) {
            return "WAITING_DELIVERY";
        }
        if (type == ru.sivak.domain.order.custom.ReadyForPickUpState.class) {
            return "READY_FOR_PICK_UP";
        }
        if (type == ru.sivak.domain.order.custom.CompletedState.class) {
            return "COMPLETED";
        }
        if (type == ru.sivak.domain.order.custom.CanceledState.class) {
            return "CANCELED";
        }
        throw new IllegalArgumentException("Unsupported custom order state: " + type.getName());
    }

    public static String inStockStateCode(Class<? extends InStockOrderState> type) {
        if (type == ru.sivak.domain.order.inStock.CreatedState.class) {
            return "CREATED";
        }
        if (type == ru.sivak.domain.order.inStock.ApprovedState.class) {
            return "APPROVED";
        }
        if (type == ru.sivak.domain.order.inStock.WaitingPaymentState.class) {
            return "WAITING_PAYMENT";
        }
        if (type == ru.sivak.domain.order.inStock.PaidState.class) {
            return "PAID";
        }
        if (type == ru.sivak.domain.order.inStock.ReadyForPickUpState.class) {
            return "READY_FOR_PICK_UP";
        }
        if (type == ru.sivak.domain.order.inStock.CompletedState.class) {
            return "COMPLETED";
        }
        if (type == ru.sivak.domain.order.inStock.CanceledState.class) {
            return "CANCELED";
        }
        throw new IllegalArgumentException("Unsupported in-stock order state: " + type.getName());
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

    private static void mapOrder(
            BaseOrder source,
            AbstractOrderEntity target,
            AppUserEntity manager,
            AppUserEntity client,
            CarEntity car,
            String stateCode
    ) {
        target.setId(source.getId().getId());
        target.setManager(manager);
        target.setClient(client);
        target.setCar(car);
        target.setState(stateCode);
        target.setPrice(source.getPrice().getAmount());
    }

    private static void applyCustomState(CustomOrder order, String stateCode) {
        switch (stateCode) {
            case "CREATED":
                return;
            case "APPROVED":
                order.approve();
                return;
            case "WAITING_PAYMENT":
                order.approve();
                order.requestPayment();
                return;
            case "PAID":
                order.approve();
                order.requestPayment();
                order.pay();
                return;
            case "WAITING_DELIVERY":
                order.approve();
                order.requestPayment();
                order.pay();
                order.requestDelivery();
                return;
            case "READY_FOR_PICK_UP":
                order.approve();
                order.requestPayment();
                order.pay();
                order.requestDelivery();
                order.markAsReady();
                return;
            case "COMPLETED":
                order.approve();
                order.requestPayment();
                order.pay();
                order.requestDelivery();
                order.markAsReady();
                order.complete();
                return;
            case "CANCELED":
                order.cancel();
                return;
            default:
                throw new IllegalArgumentException("Unsupported custom order state code: " + stateCode);
        }
    }

    private static void applyInStockState(InStockOrder order, String stateCode) {
        switch (stateCode) {
            case "CREATED":
                return;
            case "APPROVED":
                order.approve();
                return;
            case "WAITING_PAYMENT":
                order.approve();
                order.requestPayment();
                return;
            case "PAID":
                order.approve();
                order.requestPayment();
                order.pay();
                return;
            case "READY_FOR_PICK_UP":
                order.approve();
                order.requestPayment();
                order.pay();
                order.markAsReady();
                return;
            case "COMPLETED":
                order.approve();
                order.requestPayment();
                order.pay();
                order.markAsReady();
                order.complete();
                return;
            case "CANCELED":
                order.cancel();
                return;
            default:
                throw new IllegalArgumentException("Unsupported in-stock order state code: " + stateCode);
        }
    }
}
