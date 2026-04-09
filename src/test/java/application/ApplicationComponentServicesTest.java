package application;

import org.junit.jupiter.api.Test;
import ru.sivak.application.mappers.*;
import ru.sivak.application.query.*;
import ru.sivak.application.servicesImpl.*;
import ru.sivak.domain.entities.*;
import ru.sivak.domain.repositories.*;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApplicationComponentServicesTest {

    @Test
    void body_service_creates_updates_queries_and_deletes_body() {
        //Arrange
        BodyRepository repository = mock(BodyRepository.class);
        BodyService service = new BodyService(repository, new BodyMapperImpl());
        Body body = body("Sedan", "1000", models());

        when(repository.find(body.getId())).thenReturn(Optional.of(body), Optional.empty());
        when(repository.query(any(BodyQuery.class))).thenReturn(List.of(body));

        //Act
        service.create(body);
        service.update(body);
        BodyType actualType = service.get(body.getId()).type();
        int querySize = service.query(BodyQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1200")).build()).size();
        service.delete(body.getId());

        //Assert
        assertEquals(BodyType.of("Sedan"), actualType);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(body.getId()));
        verify(repository).create(body);
        verify(repository).update(body);
        verify(repository).delete(body.getId());
    }

    @Test
    void brand_service_creates_updates_queries_and_deletes_brand() {
        //Arrange
        BrandRepository repository = mock(BrandRepository.class);
        BrandService service = new BrandService(repository, new BrandMapperImpl());
        Brand brand = brand("Toyota", "1500", models());

        when(repository.find(brand.getId())).thenReturn(Optional.of(brand), Optional.empty());
        when(repository.query(any(BrandQuery.class))).thenReturn(List.of(brand));

        //Act
        service.create(brand);
        service.update(brand);
        BrandName actualName = service.get(brand.getId()).brandName();
        int querySize = service.query(BrandQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("2000")).build()).size();
        service.delete(brand.getId());

        //Assert
        assertEquals(BrandName.of("Toyota"), actualName);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(brand.getId()));
        verify(repository).create(brand);
        verify(repository).update(brand);
        verify(repository).delete(brand.getId());
    }

    @Test
    void color_service_creates_updates_queries_and_deletes_color() {
        //Arrange
        ColorRepository repository = mock(ColorRepository.class);
        ColorService service = new ColorService(repository, new ColorMapperImpl());
        Color color = color("Black", "300", models());

        when(repository.find(color.getId())).thenReturn(Optional.of(color), Optional.empty());
        when(repository.query(any(ColorQuery.class))).thenReturn(List.of(color));

        //Act
        service.create(color);
        service.update(color);
        ColorValue actualColor = service.get(color.getId()).color();
        int querySize = service.query(ColorQuery.builder().color(ColorValue.of("Black")).modelName(ModelName.of("Camry")).build()).size();
        service.delete(color.getId());

        //Assert
        assertEquals(ColorValue.of("Black"), actualColor);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(color.getId()));
        verify(repository).create(color);
        verify(repository).update(color);
        verify(repository).delete(color.getId());
    }

    @Test
    void drive_service_creates_updates_queries_and_deletes_drive() {
        //Arrange
        DriveRepository repository = mock(DriveRepository.class);
        DriveService service = new DriveService(repository, new DriveMapperImpl());
        Drive drive = drive("FWD", "700", models());

        when(repository.find(drive.getId())).thenReturn(Optional.of(drive), Optional.empty());
        when(repository.query(any(DriveQuery.class))).thenReturn(List.of(drive));

        //Act
        service.create(drive);
        service.update(drive);
        DriveType actualType = service.get(drive.getId()).driveType();
        int querySize = service.query(DriveQuery.builder().driveType(DriveType.of("FWD")).modelName(ModelName.of("Camry")).build()).size();
        service.delete(drive.getId());

        //Assert
        assertEquals(DriveType.of("FWD"), actualType);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(drive.getId()));
        verify(repository).create(drive);
        verify(repository).update(drive);
        verify(repository).delete(drive.getId());
    }

    @Test
    void engine_service_creates_updates_queries_and_deletes_engine() {
        //Arrange
        EngineRepository repository = mock(EngineRepository.class);
        EngineService service = new EngineService(repository, new EngineMapperImpl());
        Engine engine = engine(220, 2000, "2400", models());

        when(repository.find(engine.getId())).thenReturn(Optional.of(engine), Optional.empty());
        when(repository.query(any(EngineQuery.class))).thenReturn(List.of(engine));

        //Act
        service.create(engine);
        service.update(engine);
        EnginePower actualPower = service.get(engine.getId()).power();
        int querySize = service.query(EngineQuery.builder().power(EnginePower.of(220)).volume(EngineVolume.of(2000)).modelName(ModelName.of("Camry")).build()).size();
        service.delete(engine.getId());

        //Assert
        assertEquals(EnginePower.of(220), actualPower);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(engine.getId()));
        verify(repository).create(engine);
        verify(repository).update(engine);
        verify(repository).delete(engine.getId());
    }

    @Test
    void fuel_service_creates_updates_queries_and_deletes_fuel() {
        //Arrange
        FuelRepository repository = mock(FuelRepository.class);
        FuelService service = new FuelService(repository, new FuelMapperImpl());
        Fuel fuel = fuel("Petrol", "200", models());

        when(repository.find(fuel.getId())).thenReturn(Optional.of(fuel), Optional.empty());
        when(repository.query(any(FuelQuery.class))).thenReturn(List.of(fuel));

        //Act
        service.create(fuel);
        service.update(fuel);
        FuelType actualType = service.get(fuel.getId()).fuelType();
        int querySize = service.query(FuelQuery.builder().fuelType(FuelType.of("Petrol")).modelName(ModelName.of("Camry")).build()).size();
        service.delete(fuel.getId());

        //Assert
        assertEquals(FuelType.of("Petrol"), actualType);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(fuel.getId()));
        verify(repository).create(fuel);
        verify(repository).update(fuel);
        verify(repository).delete(fuel.getId());
    }

    @Test
    void interior_service_creates_updates_queries_and_deletes_interior() {
        //Arrange
        InteriorRepository repository = mock(InteriorRepository.class);
        InteriorService service = new InteriorService(repository, new InteriorMapperImpl());
        Interior interior = interior("900", models());

        when(repository.find(interior.getId())).thenReturn(Optional.of(interior), Optional.empty());
        when(repository.query(any(InteriorQuery.class))).thenReturn(List.of(interior));

        //Act
        service.create(interior);
        service.update(interior);
        Money actualPrice = service.get(interior.getId()).price();
        int querySize = service.query(InteriorQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1000")).build()).size();
        service.delete(interior.getId());

        //Assert
        assertEquals(interior.getPrice(), actualPrice);
        assertEquals(1, querySize);
        assertThrows(RuntimeException.class, () -> service.get(interior.getId()));
        verify(repository).create(interior);
        verify(repository).update(interior);
        verify(repository).delete(interior.getId());
    }

    @Test
    void model_service_creates_updates_queries_and_deletes_model() {
        //Arrange
        ModelRepository repository = mock(ModelRepository.class);
        ModelService service = new ModelService(repository, new ModelMapperImpl());
        Model model = model("Camry", "1200", models());

        when(repository.find(model.getId())).thenReturn(Optional.of(model), Optional.empty());
        when(repository.query(any(ModelQuery.class))).thenReturn(List.of(model));

        //Act
        service.create(model);
        service.update(model);
        ModelName actualName = service.get(model.getId()).modelName();
        int querySize = service.query(ModelQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1400")).build()).size();
        service.delete(model.getId());

        //Assert
        assertEquals(ModelName.of("Camry"), actualName);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(model.getId()));
        verify(repository).create(model);
        verify(repository).update(model);
        verify(repository).delete(model.getId());
    }

    @Test
    void steering_service_creates_updates_queries_and_deletes_steering() {
        //Arrange
        SteeringRepository repository = mock(SteeringRepository.class);
        SteeringService service = new SteeringService(repository, new SteeringMapperImpl());
        Steering steering = steering("250", models());

        when(repository.find(steering.getId())).thenReturn(Optional.of(steering), Optional.empty());
        when(repository.query(any(SteeringQuery.class))).thenReturn(List.of(steering));

        //Act
        service.create(steering);
        service.update(steering);
        Money actualPrice = service.get(steering.getId()).price();
        int querySize = service.query(SteeringQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("300")).build()).size();
        service.delete(steering.getId());

        //Assert
        assertEquals(steering.getPrice(), actualPrice);
        assertEquals(1, querySize);
        assertThrows(RuntimeException.class, () -> service.get(steering.getId()));
        verify(repository).create(steering);
        verify(repository).update(steering);
        verify(repository).delete(steering.getId());
    }

    @Test
    void transmission_service_creates_updates_queries_and_deletes_transmission() {
        //Arrange
        TransmissonRepository repository = mock(TransmissonRepository.class);
        TransmissionService service = new TransmissionService(repository, new TransmissionMapperImpl());
        Transmission transmission = transmission("example1", "1400", models());

        when(repository.find(transmission.getId())).thenReturn(Optional.of(transmission), Optional.empty());
        when(repository.query(any(TransmissionQuery.class))).thenReturn(List.of(transmission));

        //Act
        service.create(transmission);
        service.update(transmission);
        TransmissionType actualType = service.get(transmission.getId()).type();
        int querySize = service.query(TransmissionQuery.builder().type(TransmissionType.of("example1")).modelName(ModelName.of("Camry")).build()).size();
        service.delete(transmission.getId());

        //Assert
        assertEquals(TransmissionType.of("example1"), actualType);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(transmission.getId()));
        verify(repository).create(transmission);
        verify(repository).update(transmission);
        verify(repository).delete(transmission.getId());
    }

    @Test
    void wheel_service_creates_updates_queries_and_deletes_wheel() {
        //Arrange
        WheelRepository repository = mock(WheelRepository.class);
        WheelService service = new WheelService(repository, new WheelMapperImpl());
        Wheel wheel = wheel("450", models());

        when(repository.find(wheel.getId())).thenReturn(Optional.of(wheel), Optional.empty());
        when(repository.query(any(WheelQuery.class))).thenReturn(List.of(wheel));

        //Act
        service.create(wheel);
        service.update(wheel);
        Money actualPrice = service.get(wheel.getId()).price();
        int querySize = service.query(WheelQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("700")).build()).size();
        service.delete(wheel.getId());

        //Assert
        assertEquals(wheel.getPrice(), actualPrice);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(wheel.getId()));
        verify(repository).create(wheel);
        verify(repository).update(wheel);
        verify(repository).delete(wheel.getId());
    }

    @Test
    void car_service_creates_updates_queries_and_deletes_car() {
        //Arrange
        CarRepository repository = mock(CarRepository.class);
        CarService service = new CarService(repository, new CarMapperImpl());
        Car car = car("Camry", "15000", models());

        when(repository.find(car.getId())).thenReturn(Optional.of(car), Optional.empty());
        when(repository.query(any(CarQuery.class))).thenReturn(List.of(car));

        //Act
        service.create(car);
        service.update(car);
        Id actualId = service.get(car.getId()).id();
        int querySize = service.query(
                CarQuery.builder()
                        .brandName(BrandName.of("Toyota"))
                        .modelName(ModelName.of("Camry"))
                        .bodyType(BodyType.of("Sedan"))
                        .color(ColorValue.of("Black"))
                        .driveType(DriveType.of("FWD"))
                        .enginePower(EnginePower.of(220))
                        .engineVolume(EngineVolume.of(2000))
                        .fuelType(FuelType.of("Petrol"))
                        .transmissionType(TransmissionType.of("example1"))
                        .minPrice(money("14000"))
                        .maxPrice(money("16000"))
                        .build()
        ).size();
        service.delete(car.getId());

        //Assert
        assertEquals(car.getId(), actualId);
        assertEquals(1, querySize);
        assertThrows(IllegalArgumentException.class, () -> service.get(car.getId()));
        verify(repository).create(car);
        verify(repository).update(car);
        verify(repository).delete(car.getId());
    }

    private static Set<ModelName> models() {
        return Set.of(ModelName.of("Camry"), ModelName.of("Corolla"));
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
                .driveType(drive("FWD", "700", suitableModels))
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
