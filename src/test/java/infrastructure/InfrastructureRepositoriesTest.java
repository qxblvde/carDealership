package infrastructure;

import org.junit.jupiter.api.Test;
import ru.sivak.application.query.BodyQuery;
import ru.sivak.application.query.BrandQuery;
import ru.sivak.application.query.CarQuery;
import ru.sivak.application.query.ColorQuery;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.application.query.DriveQuery;
import ru.sivak.application.query.EngineQuery;
import ru.sivak.application.query.FuelQuery;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.application.query.InteriorQuery;
import ru.sivak.application.query.ModelQuery;
import ru.sivak.application.query.SteeringQuery;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.order.inStock.InStockOrder;
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
import ru.sivak.infrastructure.repositories.InMemoryBodyRepository;
import ru.sivak.infrastructure.repositories.InMemoryBrandRepository;
import ru.sivak.infrastructure.repositories.InMemoryCarRepository;
import ru.sivak.infrastructure.repositories.InMemoryColorRepository;
import ru.sivak.infrastructure.repositories.InMemoryCustomOrderRepository;
import ru.sivak.infrastructure.repositories.InMemoryDriveRepository;
import ru.sivak.infrastructure.repositories.InMemoryEngineRepository;
import ru.sivak.infrastructure.repositories.InMemoryFuelRepository;
import ru.sivak.infrastructure.repositories.InMemoryInStockOrderRepository;
import ru.sivak.infrastructure.repositories.InMemoryInteriorRepository;
import ru.sivak.infrastructure.repositories.InMemoryModelRepository;
import ru.sivak.infrastructure.repositories.InMemorySteeringRepository;
import ru.sivak.infrastructure.repositories.InMemoryTestDriveRequestRepository;
import ru.sivak.infrastructure.repositories.InMemoryTransmissonRepository;
import ru.sivak.infrastructure.repositories.InMemoryWheelRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InfrastructureRepositoriesTest {

    @Test
    void body_brand_and_color_repositories_support_basic_operations() {
        //Arrange
        InMemoryBodyRepository bodyRepository = new InMemoryBodyRepository();
        InMemoryBrandRepository brandRepository = new InMemoryBrandRepository();
        InMemoryColorRepository colorRepository = new InMemoryColorRepository();
        Body body = body("Sedan", "1000", models());
        Brand brand = brand("Toyota", "1500", models());
        Color color = color("Black", "300", models());

        //Act
        bodyRepository.save(body);
        bodyRepository.save(body("SUV", "2000", corollaModels()));
        brandRepository.save(brand);
        brandRepository.save(brand("Honda", "2500", corollaModels()));
        colorRepository.save(color);
        colorRepository.save(color("White", "600", corollaModels()));
        int bodyQuerySize = bodyRepository.query(BodyQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1200")).build()).size();
        int brandQuerySize = brandRepository.query(BrandQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("2000")).build()).size();
        int colorQuerySize = colorRepository.query(ColorQuery.builder().color(ColorValue.of("Black")).modelName(ModelName.of("Camry")).maxPrice(money("400")).build()).size();
        bodyRepository.delete(body.getId());
        brandRepository.delete(brand.getId());
        colorRepository.delete(color.getId());

        //Assert
        assertEquals(1, bodyQuerySize);
        assertEquals(1, brandQuerySize);
        assertEquals(1, colorQuerySize);
        assertFalse(bodyRepository.find(body.getId()).isPresent());
        assertFalse(brandRepository.find(brand.getId()).isPresent());
        assertFalse(colorRepository.find(color.getId()).isPresent());
    }

    @Test
    void drive_engine_and_fuel_repositories_support_basic_operations() {
        //Arrange
        InMemoryDriveRepository driveRepository = new InMemoryDriveRepository();
        InMemoryEngineRepository engineRepository = new InMemoryEngineRepository();
        InMemoryFuelRepository fuelRepository = new InMemoryFuelRepository();
        Drive drive = drive("example1", "700", models());
        Engine engine = engine(220, 2000, "2400", models());
        Fuel fuel = fuel("Petrol", "200", models());

        //Act
        driveRepository.save(drive);
        driveRepository.save(drive("example2", "500", corollaModels()));
        engineRepository.save(engine);
        engineRepository.save(engine(150, 1600, "1800", corollaModels()));
        fuelRepository.save(fuel);
        fuelRepository.save(fuel("Diesel", "350", corollaModels()));
        int driveQuerySize = driveRepository.query(DriveQuery.builder().driveType(DriveType.of("example1")).modelName(ModelName.of("Camry")).build()).size();
        int engineQuerySize = engineRepository.query(EngineQuery.builder().power(EnginePower.of(220)).volume(EngineVolume.of(2000)).modelName(ModelName.of("Camry")).build()).size();
        int fuelQuerySize = fuelRepository.query(FuelQuery.builder().fuelType(FuelType.of("Petrol")).modelName(ModelName.of("Camry")).build()).size();

        //Assert
        assertEquals(1, driveQuerySize);
        assertEquals(1, engineQuerySize);
        assertEquals(1, fuelQuerySize);
    }

    @Test
    void interior_model_and_steering_repositories_support_basic_operations() {
        //Arrange
        InMemoryInteriorRepository interiorRepository = new InMemoryInteriorRepository();
        InMemoryModelRepository modelRepository = new InMemoryModelRepository();
        InMemorySteeringRepository steeringRepository = new InMemorySteeringRepository();
        Interior interior = interior("900", models());
        Model model = model("Camry", "1200", models());
        Steering steering = steering("250", models());

        //Act
        interiorRepository.save(interior);
        interiorRepository.save(interior("1200", corollaModels()));
        modelRepository.save(model);
        modelRepository.save(model("Corolla", "1800", corollaModels()));
        steeringRepository.save(steering);
        steeringRepository.save(steering("450", corollaModels()));
        int interiorQuerySize = interiorRepository.query(InteriorQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1000")).build()).size();
        int modelQuerySize = modelRepository.query(ModelQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1400")).build()).size();
        int steeringQuerySize = steeringRepository.query(SteeringQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("300")).build()).size();

        //Assert
        assertEquals(1, interiorQuerySize);
        assertEquals(1, modelQuerySize);
        assertEquals(1, steeringQuerySize);
    }

    @Test
    void transmission_and_wheel_repositories_support_basic_operations() {
        //Arrange
        InMemoryTransmissonRepository transmissionRepository = new InMemoryTransmissonRepository();
        InMemoryWheelRepository wheelRepository = new InMemoryWheelRepository();
        Transmission transmission = transmission("example1", "1400", models());
        Wheel wheel = wheel("450", models());

        //Act
        transmissionRepository.save(transmission);
        transmissionRepository.save(transmission("example2", "1100", corollaModels()));
        wheelRepository.save(wheel);
        wheelRepository.save(wheel("650", corollaModels()));
        int transmissionQuerySize = transmissionRepository.query(TransmissionQuery.builder().type(TransmissionType.of("example1")).modelName(ModelName.of("Camry")).build()).size();
        int wheelQuerySize = wheelRepository.query(WheelQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("700")).build()).size();

        //Assert
        assertEquals(1, transmissionQuerySize);
        assertEquals(1, wheelQuerySize);
    }

    @Test
    void car_repository_filters_by_full_query() {
        //Arrange
        InMemoryCarRepository repository = new InMemoryCarRepository();

        //Act
        repository.save(car("Camry", "15000", models()));
        repository.save(car("Corolla", "21000", corollaModels()));
        int querySize = repository.query(
                CarQuery.builder()
                        .brandName(BrandName.of("Toyota"))
                        .modelName(ModelName.of("Camry"))
                        .bodyType(BodyType.of("Sedan"))
                        .color(ColorValue.of("Black"))
                        .driveType(DriveType.of("example1"))
                        .enginePower(EnginePower.of(220))
                        .engineVolume(EngineVolume.of(2000))
                        .fuelType(FuelType.of("Petrol"))
                        .minPrice(money("14000"))
                        .maxPrice(money("16000"))
                        .build()
        ).size();

        //Assert
        assertEquals(1, querySize);
    }

    @Test
    void custom_order_repository_queries_and_deletes_orders() {
        //Arrange
        InMemoryCustomOrderRepository repository = new InMemoryCustomOrderRepository();
        CustomOrder order = new CustomOrder(Id.newId(), Id.newId(), Id.newId(), car("Camry", "15000", models()));
        order.approve();

        //Act
        repository.save(order);
        repository.save(new CustomOrder(Id.newId(), Id.newId(), Id.newId(), car("Corolla", "21000", corollaModels())));
        int querySize = repository.query(CustomOrderQuery.builder().clientId(order.getClientId()).managerId(order.getManagerId()).minPrice(money("14000")).maxPrice(money("16000")).build()).size();
        repository.delete(order.getId());

        //Assert
        assertEquals(1, querySize);
        assertFalse(repository.find(order.getId()).isPresent());
    }

    @Test
    void in_stock_order_repository_queries_and_deletes_orders() {
        //Arrange
        InMemoryInStockOrderRepository repository = new InMemoryInStockOrderRepository();
        InStockOrder order = new InStockOrder(Id.newId(), Id.newId(), Id.newId(), car("Camry", "15000", models()));
        order.updateClient(order.getClientId());

        //Act
        repository.save(order);
        repository.save(new InStockOrder(Id.newId(), Id.newId(), Id.newId(), car("Corolla", "21000", corollaModels())));
        int querySize = repository.query(InStockOrderQuery.builder().clientId(order.getClientId()).managerId(order.getManagerId()).minPrice(money("14000")).maxPrice(money("16000")).build()).size();
        repository.delete(order.getId());

        //Assert
        assertEquals(1, querySize);
        assertFalse(repository.find(order.getId()).isPresent());
    }

    @Test
    void request_repository_queries_and_deletes_requests() {
        //Arrange
        InMemoryTestDriveRequestRepository repository = new InMemoryTestDriveRequestRepository();
        TestDriveRequest request = TestDriveRequest.builder()
                .id(Id.newId())
                .clientId(Id.newId())
                .carId(Id.newId())
                .scheduledTime(LocalDate.of(2026, 3, 12))
                .build();

        //Act
        repository.save(request);
        repository.save(TestDriveRequest.builder().id(Id.newId()).clientId(Id.newId()).carId(Id.newId()).scheduledTime(LocalDate.of(2026, 3, 20)).build());
        int querySize = repository.query(TestDriveRequestQuery.builder().clientId(request.getClientId()).carId(request.getCarId()).fromDate(LocalDate.of(2026, 3, 11)).toDate(LocalDate.of(2026, 3, 13)).build()).size();
        repository.delete(request.getId());

        //Assert
        assertEquals(1, querySize);
        assertTrue(repository.find(request.getId()).isEmpty());
    }

    private static Set<ModelName> models() {
        return Set.of(ModelName.of("Camry"), ModelName.of("Corolla"));
    }

    private static Set<ModelName> corollaModels() {
        return Set.of(ModelName.of("Corolla"));
    }

    private static Money money(String amount) {
        return Money.of(new BigDecimal(amount));
    }

    private static Body body(String type, String price, Set<ModelName> suitableModels) {
        return new Body(Id.newId(), money(price), ComponentName.of("Body"), suitableModels, BodyType.of(type));
    }

    private static Brand brand(String name, String price, Set<ModelName> suitableModels) {
        return new Brand(Id.newId(), money(price), ComponentName.of("Brand"), suitableModels, BrandName.of(name));
    }

    private static Color color(String value, String price, Set<ModelName> suitableModels) {
        return new Color(Id.newId(), money(price), ComponentName.of("Color"), suitableModels, ColorValue.of(value));
    }

    private static Drive drive(String value, String price, Set<ModelName> suitableModels) {
        return new Drive(Id.newId(), money(price), ComponentName.of("Drive"), suitableModels, DriveType.of(value));
    }

    private static Engine engine(int power, int volume, String price, Set<ModelName> suitableModels) {
        return new Engine(Id.newId(), money(price), ComponentName.of("Engine"), suitableModels, EnginePower.of(power), EngineVolume.of(volume));
    }

    private static Fuel fuel(String value, String price, Set<ModelName> suitableModels) {
        return new Fuel(Id.newId(), money(price), ComponentName.of("Fuel"), suitableModels, FuelType.of(value));
    }

    private static Interior interior(String price, Set<ModelName> suitableModels) {
        return new Interior(Id.newId(), money(price), ComponentName.of("Interior"), suitableModels);
    }

    private static Model model(String value, String price, Set<ModelName> suitableModels) {
        return new Model(Id.newId(), money(price), ComponentName.of("Model"), suitableModels, ModelName.of(value));
    }

    private static Steering steering(String price, Set<ModelName> suitableModels) {
        return new Steering(Id.newId(), money(price), ComponentName.of("Steering"), suitableModels);
    }

    private static Transmission transmission(String value, String price, Set<ModelName> suitableModels) {
        return new Transmission(Id.newId(), money(price), ComponentName.of("Transmission"), suitableModels, TransmissionType.of(value));
    }

    private static Wheel wheel(String price, Set<ModelName> suitableModels) {
        return new Wheel(Id.newId(), money(price), ComponentName.of("Wheel"), suitableModels);
    }

    private static Car car(String modelName, String price, Set<ModelName> suitableModels) {
        return Car.builder()
                .id(Id.newId())
                .bodyType(body("Sedan", "1000", suitableModels))
                .brandName(brand("Toyota", "1500", suitableModels))
                .color(color("Black", "300", suitableModels))
                .driveType(drive("example1", "700", suitableModels))
                .engine(engine(220, 2000, "2400", suitableModels))
                .fuel(fuel("Petrol", "200", suitableModels))
                .model(model(modelName, "1200", suitableModels))
                .price(money(price))
                .transmission(transmission("example1", "1400", suitableModels))
                .steering(steering("250", suitableModels))
                .wheel(wheel("450", suitableModels))
                .interior(interior("900", suitableModels))
                .build();
    }
}
