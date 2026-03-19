package application;

import org.junit.jupiter.api.Test;
import ru.sivak.application.query.BodyQuery;
import ru.sivak.application.query.BrandQuery;
import ru.sivak.application.query.CarQuery;
import ru.sivak.application.query.ColorQuery;
import ru.sivak.application.query.DriveQuery;
import ru.sivak.application.query.EngineQuery;
import ru.sivak.application.query.FuelQuery;
import ru.sivak.application.query.InteriorQuery;
import ru.sivak.application.query.ModelQuery;
import ru.sivak.application.query.SteeringQuery;
import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.application.servicesImpl.BodyService;
import ru.sivak.application.servicesImpl.BrandService;
import ru.sivak.application.servicesImpl.CarService;
import ru.sivak.application.servicesImpl.ColorService;
import ru.sivak.application.servicesImpl.DriveService;
import ru.sivak.application.servicesImpl.EngineService;
import ru.sivak.application.servicesImpl.FuelService;
import ru.sivak.application.servicesImpl.InteriorService;
import ru.sivak.application.servicesImpl.ModelService;
import ru.sivak.application.servicesImpl.SteeringService;
import ru.sivak.application.servicesImpl.TransmissionService;
import ru.sivak.application.servicesImpl.WheelService;
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
import ru.sivak.infrastructure.repositories.InMemoryBodyRepository;
import ru.sivak.infrastructure.repositories.InMemoryBrandRepository;
import ru.sivak.infrastructure.repositories.InMemoryCarRepository;
import ru.sivak.infrastructure.repositories.InMemoryColorRepository;
import ru.sivak.infrastructure.repositories.InMemoryDriveRepository;
import ru.sivak.infrastructure.repositories.InMemoryEngineRepository;
import ru.sivak.infrastructure.repositories.InMemoryFuelRepository;
import ru.sivak.infrastructure.repositories.InMemoryInteriorRepository;
import ru.sivak.infrastructure.repositories.InMemoryModelRepository;
import ru.sivak.infrastructure.repositories.InMemorySteeringRepository;
import ru.sivak.infrastructure.repositories.InMemoryTransmissonRepository;
import ru.sivak.infrastructure.repositories.InMemoryWheelRepository;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationComponentServicesTest {

    @Test
    void body_service_saves_queries_and_deletes_body() {
        //Arrange
        BodyService service = new BodyService(new InMemoryBodyRepository());
        Body body = body("Sedan", "1000", models());
        Body secondBody = body("SUV", "2000", corollaModels());

        //Act
        service.save(body);
        service.save(secondBody);
        BodyType actualType = service.get(body.getId()).type();
        int querySize = service.query(BodyQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1200")).build()).size();
        service.delete(body.getId());

        //Assert
        assertEquals(BodyType.of("Sedan"), actualType);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(body.getId()));
    }

    @Test
    void brand_service_saves_queries_and_deletes_brand() {
        //Arrange
        BrandService service = new BrandService(new InMemoryBrandRepository());
        Brand brand = brand("Toyota", "1500", models());
        Brand secondBrand = brand("Honda", "2500", corollaModels());

        //Act
        service.save(brand);
        service.save(secondBrand);
        BrandName actualName = service.get(brand.getId()).brandName();
        int querySize = service.query(BrandQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("2000")).build()).size();
        service.delete(brand.getId());

        //Assert
        assertEquals(BrandName.of("Toyota"), actualName);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(brand.getId()));
    }

    @Test
    void color_service_saves_queries_and_deletes_color() {
        //Arrange
        ColorService service = new ColorService(new InMemoryColorRepository());
        Color color = color("Black", "300", models());
        Color secondColor = color("White", "600", corollaModels());

        //Act
        service.save(color);
        service.save(secondColor);
        ColorValue actualColor = service.get(color.getId()).color();
        int querySize = service.query(ColorQuery.builder().color(ColorValue.of("Black")).modelName(ModelName.of("Camry")).build()).size();
        service.delete(color.getId());

        //Assert
        assertEquals(ColorValue.of("Black"), actualColor);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(color.getId()));
    }

    @Test
    void drive_service_saves_queries_and_deletes_drive() {
        //Arrange
        DriveService service = new DriveService(new InMemoryDriveRepository());
        Drive drive = drive("example1", "700", models());
        Drive secondDrive = drive("example2", "500", corollaModels());

        //Act
        service.save(drive);
        service.save(secondDrive);
        DriveType actualType = service.get(drive.getId()).driveType();
        int querySize = service.query(DriveQuery.builder().driveType(DriveType.of("example1")).modelName(ModelName.of("Camry")).build()).size();
        service.delete(drive.getId());

        //Assert
        assertEquals(DriveType.of("example1"), actualType);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(drive.getId()));
    }

    @Test
    void engine_service_saves_queries_and_deletes_engine() {
        //Arrange
        EngineService service = new EngineService(new InMemoryEngineRepository());
        Engine engine = engine(220, 2000, "2400", models());
        Engine secondEngine = engine(150, 1600, "1800", corollaModels());

        //Act
        service.save(engine);
        service.save(secondEngine);
        EnginePower actualPower = service.get(engine.getId()).power();
        int querySize = service.query(EngineQuery.builder().power(EnginePower.of(220)).volume(EngineVolume.of(2000)).modelName(ModelName.of("Camry")).build()).size();
        service.delete(engine.getId());

        //Assert
        assertEquals(EnginePower.of(220), actualPower);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(engine.getId()));
    }

    @Test
    void fuel_service_saves_queries_and_deletes_fuel() {
        //Arrange
        FuelService service = new FuelService(new InMemoryFuelRepository());
        Fuel fuel = fuel("Petrol", "200", models());
        Fuel secondFuel = fuel("Diesel", "350", corollaModels());

        //Act
        service.save(fuel);
        service.save(secondFuel);
        FuelType actualType = service.get(fuel.getId()).fuelType();
        int querySize = service.query(FuelQuery.builder().fuelType(FuelType.of("Petrol")).modelName(ModelName.of("Camry")).build()).size();
        service.delete(fuel.getId());

        //Assert
        assertEquals(FuelType.of("Petrol"), actualType);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(fuel.getId()));
    }

    @Test
    void interior_service_saves_queries_and_deletes_interior() {
        //Arrange
        InteriorService service = new InteriorService(new InMemoryInteriorRepository());
        Interior interior = interior("900", models());
        Interior secondInterior = interior("1200", corollaModels());

        //Act
        service.save(interior);
        service.save(secondInterior);
        Money actualPrice = service.get(interior.getId()).price();
        int querySize = service.query(InteriorQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1000")).build()).size();
        service.delete(interior.getId());

        //Assert
        assertEquals(interior.getPrice(), actualPrice);
        assertEquals(1, querySize);
        assertThrows(RuntimeException.class, () -> service.get(interior.getId()));
    }

    @Test
    void model_service_saves_queries_and_deletes_model() {
        //Arrange
        ModelService service = new ModelService(new InMemoryModelRepository());
        Model model = model("Camry", "1200", models());
        Model secondModel = model("Corolla", "1800", corollaModels());

        //Act
        service.save(model);
        service.save(secondModel);
        ModelName actualName = service.get(model.getId()).modelName();
        int querySize = service.query(ModelQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1400")).build()).size();
        service.delete(model.getId());

        //Assert
        assertEquals(ModelName.of("Camry"), actualName);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(model.getId()));
    }

    @Test
    void steering_service_saves_queries_and_deletes_steering() {
        //Arrange
        SteeringService service = new SteeringService(new InMemorySteeringRepository());
        Steering steering = steering("250", models());
        Steering secondSteering = steering("450", corollaModels());

        //Act
        service.save(steering);
        service.save(secondSteering);
        Money actualPrice = service.get(steering.getId()).price();
        int querySize = service.query(SteeringQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("300")).build()).size();
        service.delete(steering.getId());

        //Assert
        assertEquals(steering.getPrice(), actualPrice);
        assertEquals(1, querySize);
        assertThrows(RuntimeException.class, () -> service.get(steering.getId()));
    }

    @Test
    void transmission_service_saves_queries_and_deletes_transmission() {
        //Arrange
        TransmissionService service = new TransmissionService(new InMemoryTransmissonRepository());
        Transmission transmission = transmission("example1", "1400", models());
        Transmission secondTransmission = transmission("example2", "1100", corollaModels());

        //Act
        service.save(transmission);
        service.save(secondTransmission);
        TransmissionType actualType = service.get(transmission.getId()).type();
        int querySize = service.query(TransmissionQuery.builder().type(TransmissionType.of("example1")).modelName(ModelName.of("Camry")).build()).size();
        service.delete(transmission.getId());

        //Assert
        assertEquals(TransmissionType.of("example1"), actualType);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(transmission.getId()));
    }

    @Test
    void wheel_service_saves_queries_and_deletes_wheel() {
        //Arrange
        WheelService service = new WheelService(new InMemoryWheelRepository());
        Wheel wheel = wheel("450", models());
        Wheel secondWheel = wheel("650", corollaModels());

        //Act
        service.save(wheel);
        service.save(secondWheel);
        Money actualPrice = service.get(wheel.getId()).price();
        int querySize = service.query(WheelQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("700")).build()).size();
        service.delete(wheel.getId());

        //Assert
        assertEquals(wheel.getPrice(), actualPrice);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(wheel.getId()));
    }

    @Test
    void car_service_saves_queries_and_deletes_car() {
        //Arrange
        CarService service = new CarService(new InMemoryCarRepository());
        Car car = car("Camry", "15000", models());
        Car secondCar = car("Corolla", "21000", corollaModels());

        //Act
        service.save(car);
        service.save(secondCar);
        Id actualId = service.get(car.getId()).id();
        int querySize = service.query(
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
        service.delete(car.getId());

        //Assert
        assertEquals(car.getId(), actualId);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(car.getId()));
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
