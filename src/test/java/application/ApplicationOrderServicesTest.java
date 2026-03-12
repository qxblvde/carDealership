package application;

import org.junit.jupiter.api.Test;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.application.servicesImpl.CustomOrderService;
import ru.sivak.application.servicesImpl.InStockOrderService;
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
import ru.sivak.domain.order.custom.CompletedState;
import ru.sivak.domain.order.custom.CreatedState;
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
import ru.sivak.infrastructure.repositories.InMemoryCarRepository;
import ru.sivak.infrastructure.repositories.InMemoryCustomOrderRepository;
import ru.sivak.infrastructure.repositories.InMemoryInStockOrderRepository;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationOrderServicesTest {

    @Test
    void custom_order_service_creates_order() {
        //Arrange
        InMemoryCarRepository carRepository = new InMemoryCarRepository();
        CustomOrderService service = new CustomOrderService(new InMemoryCustomOrderRepository(), carRepository);
        Id clientId = Id.newId();
        Car car = car("Camry", "15000", models());
        carRepository.save(car);

        //Act
        OrderDto created = service.create(clientId, car.getId());

        //Assert
        assertEquals(clientId, created.clientId());
        assertEquals(CreatedState.class, service.get(created.id()).getStateType());
    }

    @Test
    void custom_order_service_updates_client_and_total() {
        //Arrange
        InMemoryCarRepository carRepository = new InMemoryCarRepository();
        CustomOrderService service = new CustomOrderService(new InMemoryCustomOrderRepository(), carRepository);
        Car firstCar = car("Camry", "15000", models());
        Car secondCar = car("Corolla", "21000", corollaModels());
        carRepository.save(firstCar);
        carRepository.save(secondCar);
        OrderDto created = service.create(Id.newId(), firstCar.getId());
        Id newClientId = Id.newId();

        //Act
        OrderDto updated = service.update(created.id(), newClientId, secondCar.getId());

        //Assert
        assertEquals(newClientId, updated.clientId());
        assertEquals(secondCar.getPrice(), updated.total());
    }

    @Test
    void custom_order_service_queries_by_client_and_state() {
        //Arrange
        InMemoryCarRepository carRepository = new InMemoryCarRepository();
        CustomOrderService service = new CustomOrderService(new InMemoryCustomOrderRepository(), carRepository);
        Id clientId = Id.newId();
        Car car = car("Camry", "15000", models());
        carRepository.save(car);
        OrderDto created = service.create(clientId, car.getId());

        //Act
        int sizeByState = service.query(CustomOrderQuery.builder().clientId(clientId).stateType(CreatedState.class).build()).size();
        Id foundId = service.query(CustomOrderQuery.builder().clientId(clientId).build()).get(0).id();

        //Assert
        assertEquals(1, sizeByState);
        assertEquals(created.id(), foundId);
    }

    @Test
    void custom_order_service_completes_order() {
        //Arrange
        InMemoryCarRepository carRepository = new InMemoryCarRepository();
        CustomOrderService service = new CustomOrderService(new InMemoryCustomOrderRepository(), carRepository);
        Id clientId = Id.newId();
        Car car = car("Camry", "15000", models());
        carRepository.save(car);
        OrderDto created = service.create(clientId, car.getId());

        //Act
        service.approve(created.id());
        service.requestPayment(created.id());
        service.pay(created.id());
        service.requestDelivery(created.id());
        service.markAsReady(created.id());
        service.complete(created.id());

        //Assert
        assertEquals(CompletedState.class, service.get(created.id()).getStateType());
    }

    @Test
    void custom_order_service_throws_for_missing_car_and_order() {
        //Arrange
        CustomOrderService service = new CustomOrderService(new InMemoryCustomOrderRepository(), new InMemoryCarRepository());

        //Act
        IllegalArgumentException createException = assertThrows(IllegalArgumentException.class, () -> service.create(Id.newId(), Id.newId()));
        IllegalArgumentException getException = assertThrows(IllegalArgumentException.class, () -> service.get(Id.newId()));

        //Assert
        assertEquals(IllegalArgumentException.class, createException.getClass());
        assertEquals(IllegalArgumentException.class, getException.getClass());
    }

    @Test
    void in_stock_order_service_creates_order() {
        //Arrange
        InMemoryCarRepository carRepository = new InMemoryCarRepository();
        InStockOrderService service = new InStockOrderService(new InMemoryInStockOrderRepository(), carRepository);
        Id clientId = Id.newId();
        Car car = car("Camry", "15000", models());
        carRepository.save(car);

        //Act
        OrderDto created = service.create(clientId, car.getId());

        //Assert
        assertEquals(clientId, created.clientId());
        assertEquals(ru.sivak.domain.order.inStock.CreatedState.class, service.get(created.id()).getStateType());
    }

    @Test
    void in_stock_order_service_updates_client_and_total() {
        //Arrange
        InMemoryCarRepository carRepository = new InMemoryCarRepository();
        InStockOrderService service = new InStockOrderService(new InMemoryInStockOrderRepository(), carRepository);
        Car firstCar = car("Camry", "15000", models());
        Car secondCar = car("Corolla", "21000", corollaModels());
        carRepository.save(firstCar);
        carRepository.save(secondCar);
        OrderDto created = service.create(Id.newId(), firstCar.getId());
        Id newClientId = Id.newId();

        //Act
        OrderDto updated = service.update(created.id(), newClientId, secondCar.getId());

        //Assert
        assertEquals(newClientId, updated.clientId());
        assertEquals(secondCar.getPrice(), updated.total());
    }

    @Test
    void in_stock_order_service_queries_by_client_and_state() {
        //Arrange
        InMemoryCarRepository carRepository = new InMemoryCarRepository();
        InStockOrderService service = new InStockOrderService(new InMemoryInStockOrderRepository(), carRepository);
        Id clientId = Id.newId();
        Car car = car("Camry", "15000", models());
        carRepository.save(car);
        OrderDto created = service.create(clientId, car.getId());
        InStockOrder order = service.get(created.id());

        //Act
        int size = service.query(
                InStockOrderQuery.builder()
                        .clientId(clientId)
                        .managerId(order.getManagerId())
                        .stateType(ru.sivak.domain.order.inStock.CreatedState.class)
                        .build()
        ).size();

        //Assert
        assertEquals(1, size);
    }

    @Test
    void in_stock_order_service_completes_order() {
        //Arrange
        InMemoryCarRepository carRepository = new InMemoryCarRepository();
        InStockOrderService service = new InStockOrderService(new InMemoryInStockOrderRepository(), carRepository);
        Id clientId = Id.newId();
        Car car = car("Camry", "15000", models());
        carRepository.save(car);
        OrderDto created = service.create(clientId, car.getId());

        //Act
        service.approve(created.id());
        service.requestPayment(created.id());
        service.pay(created.id());
        service.markAsReady(created.id());
        service.complete(created.id());

        //Assert
        assertEquals(ru.sivak.domain.order.inStock.CompletedState.class, service.get(created.id()).getStateType());
    }

    @Test
    void in_stock_order_service_throws_for_missing_car_and_order() {
        //Arrange
        InStockOrderService service = new InStockOrderService(new InMemoryInStockOrderRepository(), new InMemoryCarRepository());

        //Act
        IllegalArgumentException createException = assertThrows(IllegalArgumentException.class, () -> service.create(Id.newId(), Id.newId()));
        IllegalArgumentException getException = assertThrows(IllegalArgumentException.class, () -> service.get(Id.newId()));

        //Assert
        assertEquals(IllegalArgumentException.class, createException.getClass());
        assertEquals(IllegalArgumentException.class, getException.getClass());
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
