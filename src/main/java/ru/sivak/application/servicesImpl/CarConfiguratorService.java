package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.CarDto;
import ru.sivak.application.mappers.CarMapper;
import ru.sivak.application.services.ICarConfiguratorService;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.entities.CarConfigurator;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.entities.Component;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.exceptions.DomainException;
import ru.sivak.domain.repositories.BodyRepository;
import ru.sivak.domain.repositories.BrandRepository;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.repositories.ColorRepository;
import ru.sivak.domain.repositories.DriveRepository;
import ru.sivak.domain.repositories.EngineRepository;
import ru.sivak.domain.repositories.FuelRepository;
import ru.sivak.domain.repositories.InteriorRepository;
import ru.sivak.domain.repositories.ModelRepository;
import ru.sivak.domain.repositories.Repository;
import ru.sivak.domain.repositories.SteeringRepository;
import ru.sivak.domain.repositories.TransmissonRepository;
import ru.sivak.domain.repositories.WheelRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarConfiguratorService implements ICarConfiguratorService {
    @NonNull
    private final CarRepository carRepository;
    @NonNull
    private final BodyRepository bodyRepository;
    @NonNull
    private final BrandRepository brandRepository;
    @NonNull
    private final ColorRepository colorRepository;
    @NonNull
    private final DriveRepository driveRepository;
    @NonNull
    private final EngineRepository engineRepository;
    @NonNull
    private final FuelRepository fuelRepository;
    @NonNull
    private final ModelRepository modelRepository;
    @NonNull
    private final TransmissonRepository transmissionRepository;
    @NonNull
    private final SteeringRepository steeringRepository;
    @NonNull
    private final WheelRepository wheelRepository;
    @NonNull
    private final InteriorRepository interiorRepository;

    @NonNull
    private final CarMapper carMapper;

    @Override
    public CarDto createFromComponents(
            @NonNull Id bodyId,
            @NonNull Id brandId,
            @NonNull Id colorId,
            @NonNull Id driveId,
            @NonNull Id engineId,
            @NonNull Id fuelId,
            @NonNull Id modelId,
            @NonNull Id transmissionId,
            @NonNull Id steeringId,
            @NonNull Id wheelId,
            @NonNull Id interiorId
    ) {
        Body body = findRequired(bodyRepository, bodyId, "Body");
        Brand brand = findRequired(brandRepository, brandId, "Brand");
        Color color = findRequired(colorRepository, colorId, "Color");
        Drive drive = findRequired(driveRepository, driveId, "Drive");
        Engine engine = findRequired(engineRepository, engineId, "Engine");
        Fuel fuel = findRequired(fuelRepository, fuelId, "Fuel");
        Model model = findRequired(modelRepository, modelId, "Model");
        Transmission transmission = findRequired(transmissionRepository, transmissionId, "Transmission");
        Steering steering = findRequired(steeringRepository, steeringId, "Steering");
        Wheel wheel = findRequired(wheelRepository, wheelId, "Wheel");
        Interior interior = findRequired(interiorRepository, interiorId, "Interior");

        Id carId = Id.newId();
        Car preCar = buildCar(
                carId,
                body,
                brand,
                color,
                drive,
                engine,
                fuel,
                model,
                transmission,
                steering,
                wheel,
                interior,
                Money.of(BigDecimal.ZERO)
        );

        Money calculatedPrice;
        try {
            CarConfigurator configurator = new CarConfigurator(preCar);
            for (Component component : List.of(body, brand, color, drive, engine, fuel, model, transmission, steering, wheel, interior)) {
                configurator.addComponent(component);
            }
            configurator.validate();
            calculatedPrice = configurator.calculatePrice();
        } catch (DomainException exception) {
            throw new IllegalArgumentException(exception.getMessage());
        }

        Car configured = buildCar(
                carId,
                body,
                brand,
                color,
                drive,
                engine,
                fuel,
                model,
                transmission,
                steering,
                wheel,
                interior,
                calculatedPrice
        );

        carRepository.create(configured);
        return carMapper.map(configured);
    }

    private Car buildCar(
            Id id,
            Body body,
            Brand brand,
            Color color,
            Drive drive,
            Engine engine,
            Fuel fuel,
            Model model,
            Transmission transmission,
            Steering steering,
            Wheel wheel,
            Interior interior,
            Money price
    ) {
        return Car.builder()
                .id(id)
                .bodyType(body)
                .brandName(brand)
                .color(color)
                .driveType(drive)
                .engine(engine)
                .fuel(fuel)
                .model(model)
                .price(price)
                .transmission(transmission)
                .steering(steering)
                .wheel(wheel)
                .interior(interior)
                .build();
    }

    private <T> T findRequired(Repository<T> repository, Id id, String label) {
        return repository.find(id)
                .orElseThrow(() -> new IllegalArgumentException(label + " not found"));
    }
}
