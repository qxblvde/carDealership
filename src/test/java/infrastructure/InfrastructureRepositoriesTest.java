package infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.sivak.Main;
import ru.sivak.application.query.*;
import ru.sivak.domain.entities.*;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.order.custom.CreatedState;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.order.inStock.WaitingPaymentState;
import ru.sivak.domain.repositories.*;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@Transactional
@ActiveProfiles("test")
@SpringBootTest(classes = Main.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class InfrastructureRepositoriesTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("car_dealership_test")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void registerDataSource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.datasource.driver-class-name", POSTGRES::getDriverClassName);
    }

    @Autowired
    private BodyRepository bodyRepository;
    @Autowired
    private BrandRepository brandRepository;
    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private DriveRepository driveRepository;
    @Autowired
    private EngineRepository engineRepository;
    @Autowired
    private FuelRepository fuelRepository;
    @Autowired
    private InteriorRepository interiorRepository;
    @Autowired
    private ModelRepository modelRepository;
    @Autowired
    private SteeringRepository steeringRepository;
    @Autowired
    private TransmissonRepository transmissonRepository;
    @Autowired
    private WheelRepository wheelRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomOrderRepository customOrderRepository;
    @Autowired
    private InStockOrderRepository inStockOrderRepository;
    @Autowired
    private TestDriveRequestRepository testDriveRequestRepository;

    @Test
    void body_brand_and_color_repositories_support_basic_operations() {
        //Arrange
        Id bodyId = id("00000000-0000-0000-0000-000000000201");
        Id brandId = id("00000000-0000-0000-0000-000000000301");
        Id colorId = id("00000000-0000-0000-0000-000000000401");

        //Act
        int bodyQuerySize = bodyRepository.query(BodyQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1200")).build()).size();
        int brandQuerySize = brandRepository.query(BrandQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("2000")).build()).size();
        int colorQuerySize = colorRepository.query(ColorQuery.builder().color(ColorValue.of("Black")).modelName(ModelName.of("Camry")).maxPrice(money("400")).build()).size();

        //Assert
        assertTrue(bodyRepository.find(bodyId).isPresent());
        assertTrue(brandRepository.find(brandId).isPresent());
        assertTrue(colorRepository.find(colorId).isPresent());

        bodyRepository.delete(bodyId);
        brandRepository.delete(brandId);
        colorRepository.delete(colorId);

        assertEquals(1, bodyQuerySize);
        assertEquals(1, brandQuerySize);
        assertEquals(1, colorQuerySize);
        assertTrue(bodyRepository.find(bodyId).isEmpty());
        assertTrue(brandRepository.find(brandId).isEmpty());
        assertTrue(colorRepository.find(colorId).isEmpty());
    }

    @Test
    void drive_engine_and_fuel_repositories_support_basic_operations() {
        //Arrange
        int driveQuerySize = driveRepository.query(DriveQuery.builder().driveType(DriveType.of("FWD")).modelName(ModelName.of("Camry")).build()).size();
        int engineQuerySize = engineRepository.query(EngineQuery.builder().power(EnginePower.of(220)).volume(EngineVolume.of(2000)).modelName(ModelName.of("Camry")).build()).size();
        int fuelQuerySize = fuelRepository.query(FuelQuery.builder().fuelType(FuelType.of("Petrol")).modelName(ModelName.of("Camry")).build()).size();

        //Assert
        assertEquals(1, driveQuerySize);
        assertEquals(1, engineQuerySize);
        assertEquals(1, fuelQuerySize);
        assertTrue(driveRepository.find(id("00000000-0000-0000-0000-000000000501")).isPresent());
        assertTrue(engineRepository.find(id("00000000-0000-0000-0000-000000000601")).isPresent());
        assertTrue(fuelRepository.find(id("00000000-0000-0000-0000-000000000701")).isPresent());
    }

    @Test
    void interior_model_and_steering_repositories_support_basic_operations() {
        //Arrange
        int interiorQuerySize = interiorRepository.query(InteriorQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1000")).build()).size();
        int modelQuerySize = modelRepository.query(ModelQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("1400")).build()).size();
        int steeringQuerySize = steeringRepository.query(SteeringQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("300")).build()).size();

        //Assert
        assertEquals(1, interiorQuerySize);
        assertEquals(1, modelQuerySize);
        assertEquals(1, steeringQuerySize);
        assertTrue(interiorRepository.find(id("00000000-0000-0000-0000-000000001101")).isPresent());
        assertTrue(modelRepository.find(id("00000000-0000-0000-0000-000000000101")).isPresent());
        assertTrue(steeringRepository.find(id("00000000-0000-0000-0000-000000000901")).isPresent());
    }

    @Test
    void transmission_and_wheel_repositories_support_basic_operations() {
        //Arrange
        int transmissionQuerySize = transmissonRepository.query(TransmissionQuery.builder().type(TransmissionType.of("example1")).modelName(ModelName.of("Camry")).build()).size();
        int wheelQuerySize = wheelRepository.query(WheelQuery.builder().modelName(ModelName.of("Camry")).maxPrice(money("700")).build()).size();

        //Assert
        assertEquals(1, transmissionQuerySize);
        assertEquals(1, wheelQuerySize);
        assertTrue(transmissonRepository.find(id("00000000-0000-0000-0000-000000000801")).isPresent());
        assertTrue(wheelRepository.find(id("00000000-0000-0000-0000-000000001001")).isPresent());
    }

    @Test
    void car_repository_filters_by_full_query() {
        //Arrange
        int querySize = carRepository.query(
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
        //Assert
        Id carId = id("00000000-0000-0000-0000-000000002001");
        assertTrue(carRepository.find(carId).isPresent());
        carRepository.delete(carId);

        assertEquals(1, querySize);
        assertTrue(carRepository.find(carId).isEmpty());
    }

    @Test
    void custom_order_repository_queries_and_deletes_orders() {
        //Arrange
        Id orderId = id("00000000-0000-0000-0000-000000003001");
        Id clientId = id("00000000-0000-0000-0000-000000000002");
        Id managerId = id("00000000-0000-0000-0000-000000000001");

        //Act
        int querySize = customOrderRepository.query(
                CustomOrderQuery.builder()
                        .clientId(clientId)
                        .managerId(managerId)
                        .stateType(CreatedState.class)
                        .minPrice(money("14000"))
                        .maxPrice(money("16000"))
                        .build()
        ).size();

        //Assert
        assertTrue(customOrderRepository.find(orderId).isPresent());
        customOrderRepository.delete(orderId);

        assertEquals(1, querySize);
        assertTrue(customOrderRepository.find(orderId).isEmpty());
    }

    @Test
    void in_stock_order_repository_queries_and_deletes_orders() {
        //Arrange
        Id orderId = id("00000000-0000-0000-0000-000000003101");
        Id clientId = id("00000000-0000-0000-0000-000000000002");
        Id managerId = id("00000000-0000-0000-0000-000000000001");
        //Act
        int querySize = inStockOrderRepository.query(
                InStockOrderQuery.builder()
                        .clientId(clientId)
                        .managerId(managerId)
                        .stateType(WaitingPaymentState.class)
                        .minPrice(money("14000"))
                        .maxPrice(money("16000"))
                        .build()
        ).size();

        //Assert
        assertTrue(inStockOrderRepository.find(orderId).isPresent());
        inStockOrderRepository.delete(orderId);

        assertEquals(1, querySize);
        assertTrue(inStockOrderRepository.find(orderId).isEmpty());
    }

    @Test
    void request_repository_queries_and_deletes_requests() {
        //Arrange
        Id requestId = id("00000000-0000-0000-0000-000000004001");
        Id clientId = id("00000000-0000-0000-0000-000000000002");
        Id carId = id("00000000-0000-0000-0000-000000002001");

        //Act
        int querySize = testDriveRequestRepository.query(
                TestDriveRequestQuery.builder()
                        .clientId(clientId)
                        .carId(carId)
                        .fromDate(LocalDate.of(2026, 3, 24))
                        .toDate(LocalDate.of(2026, 3, 26))
                        .build()
        ).size();

        //Assert
        assertTrue(testDriveRequestRepository.find(requestId).isPresent());
        testDriveRequestRepository.delete(requestId);

        assertEquals(1, querySize);
        assertTrue(testDriveRequestRepository.find(requestId).isEmpty());
    }

    @Test
    void component_repositories_create_and_update_entities() {
        Body body = new Body(Id.newId(), money("1111"), ComponentName.of("Body"), models(), BodyType.of("Coupe"));
        bodyRepository.create(body);
        bodyRepository.update(new Body(body.getId(), money("1222"), ComponentName.of("Body"), models(), BodyType.of("Coupe")));
        assertEquals(money("1222"), bodyRepository.find(body.getId()).orElseThrow().getPrice());

        Brand brand = new Brand(Id.newId(), money("1500"), ComponentName.of("Brand"), models(), BrandName.of("Lexus"));
        brandRepository.create(brand);
        brandRepository.update(new Brand(brand.getId(), money("1600"), ComponentName.of("Brand"), models(), BrandName.of("Lexus")));
        assertEquals(money("1600"), brandRepository.find(brand.getId()).orElseThrow().getPrice());

        Color color = new Color(Id.newId(), money("300"), ComponentName.of("Color"), models(), ColorValue.of("Blue"));
        colorRepository.create(color);
        colorRepository.update(new Color(color.getId(), money("350"), ComponentName.of("Color"), models(), ColorValue.of("Blue")));
        assertEquals(money("350"), colorRepository.find(color.getId()).orElseThrow().getPrice());

        Drive drive = new Drive(Id.newId(), money("700"), ComponentName.of("Drive"), models(), DriveType.of("RWD"));
        driveRepository.create(drive);
        driveRepository.update(new Drive(drive.getId(), money("750"), ComponentName.of("Drive"), models(), DriveType.of("RWD")));
        assertEquals(money("750"), driveRepository.find(drive.getId()).orElseThrow().getPrice());

        Engine engine = new Engine(Id.newId(), money("2400"), ComponentName.of("Engine"), models(), EnginePower.of(250), EngineVolume.of(2500));
        engineRepository.create(engine);
        engineRepository.update(new Engine(engine.getId(), money("2450"), ComponentName.of("Engine"), models(), EnginePower.of(250), EngineVolume.of(2500)));
        assertEquals(money("2450"), engineRepository.find(engine.getId()).orElseThrow().getPrice());

        Fuel fuel = new Fuel(Id.newId(), money("200"), ComponentName.of("Fuel"), models(), FuelType.of("Hybrid"));
        fuelRepository.create(fuel);
        fuelRepository.update(new Fuel(fuel.getId(), money("250"), ComponentName.of("Fuel"), models(), FuelType.of("Hybrid")));
        assertEquals(money("250"), fuelRepository.find(fuel.getId()).orElseThrow().getPrice());

        Model model = new Model(Id.newId(), money("1200"), ComponentName.of("Model"), models(), ModelName.of("Prius"));
        modelRepository.create(model);
        modelRepository.update(new Model(model.getId(), money("1300"), ComponentName.of("Model"), models(), ModelName.of("Prius")));
        assertEquals(money("1300"), modelRepository.find(model.getId()).orElseThrow().getPrice());

        Transmission transmission = new Transmission(Id.newId(), money("1400"), ComponentName.of("Transmission"), models(), TransmissionType.of("auto"));
        transmissonRepository.create(transmission);
        transmissonRepository.update(new Transmission(transmission.getId(), money("1450"), ComponentName.of("Transmission"), models(), TransmissionType.of("auto")));
        assertEquals(money("1450"), transmissonRepository.find(transmission.getId()).orElseThrow().getPrice());

        Steering steering = new Steering(Id.newId(), money("250"), ComponentName.of("Steering"), models());
        steeringRepository.create(steering);
        steeringRepository.update(new Steering(steering.getId(), money("300"), ComponentName.of("Steering"), models()));
        assertEquals(money("300"), steeringRepository.find(steering.getId()).orElseThrow().getPrice());

        Wheel wheel = new Wheel(Id.newId(), money("450"), ComponentName.of("Wheel"), models());
        wheelRepository.create(wheel);
        wheelRepository.update(new Wheel(wheel.getId(), money("500"), ComponentName.of("Wheel"), models()));
        assertEquals(money("500"), wheelRepository.find(wheel.getId()).orElseThrow().getPrice());

        Interior interior = new Interior(Id.newId(), money("900"), ComponentName.of("Interior"), models());
        interiorRepository.create(interior);
        interiorRepository.update(new Interior(interior.getId(), money("950"), ComponentName.of("Interior"), models()));
        assertEquals(money("950"), interiorRepository.find(interior.getId()).orElseThrow().getPrice());
    }

    @Test
    void car_repository_creates_and_updates_car() {
        //Arrange
        Car created = Car.builder()
                .id(Id.newId())
                .bodyType(bodyFromSeed("00000000-0000-0000-0000-000000000201", "Sedan"))
                .brandName(brandFromSeed("00000000-0000-0000-0000-000000000301", "Toyota"))
                .color(colorFromSeed("00000000-0000-0000-0000-000000000401", "Black"))
                .driveType(driveFromSeed("00000000-0000-0000-0000-000000000501", "FWD"))
                .engine(engineFromSeed("00000000-0000-0000-0000-000000000601", 220, 2000))
                .fuel(fuelFromSeed("00000000-0000-0000-0000-000000000701", "Petrol"))
                .model(modelFromSeed("00000000-0000-0000-0000-000000000101", "Camry"))
                .price(money("19000"))
                .transmission(transmissionFromSeed("00000000-0000-0000-0000-000000000801", "example1"))
                .steering(steeringFromSeed("00000000-0000-0000-0000-000000000901"))
                .wheel(wheelFromSeed("00000000-0000-0000-0000-000000001001"))
                .interior(interiorFromSeed("00000000-0000-0000-0000-000000001101"))
                .build();
        carRepository.create(created);

        Car updated = Car.builder()
                .id(created.getId())
                .bodyType(bodyFromSeed("00000000-0000-0000-0000-000000000202", "SUV"))
                .brandName(brandFromSeed("00000000-0000-0000-0000-000000000302", "Honda"))
                .color(colorFromSeed("00000000-0000-0000-0000-000000000402", "White"))
                .driveType(driveFromSeed("00000000-0000-0000-0000-000000000502", "AWD"))
                .engine(engineFromSeed("00000000-0000-0000-0000-000000000602", 150, 1600))
                .fuel(fuelFromSeed("00000000-0000-0000-0000-000000000702", "Diesel"))
                .model(modelFromSeed("00000000-0000-0000-0000-000000000102", "Corolla"))
                .price(money("21000"))
                .transmission(transmissionFromSeed("00000000-0000-0000-0000-000000000802", "example2"))
                .steering(steeringFromSeed("00000000-0000-0000-0000-000000000902"))
                .wheel(wheelFromSeed("00000000-0000-0000-0000-000000001002"))
                .interior(interiorFromSeed("00000000-0000-0000-0000-000000001102"))
                .build();

        //Act
        carRepository.update(updated);

        //Assert
        assertEquals(money("21000"), carRepository.find(created.getId()).orElseThrow().getPrice());
    }

    @Test
    void order_repositories_create_and_update_orders() {
        //Arrange
        Id managerId = id("00000000-0000-0000-0000-000000000001");
        Id clientId = id("00000000-0000-0000-0000-000000000002");
        Car firstCar = carRepository.find(id("00000000-0000-0000-0000-000000002001")).orElseThrow();
        Car secondCar = carRepository.find(id("00000000-0000-0000-0000-000000002002")).orElseThrow();

        //Act
        CustomOrder customOrder = new CustomOrder(Id.newId(), managerId, clientId, firstCar);
        customOrderRepository.create(customOrder);
        customOrder.approve();
        customOrder.updateCar(secondCar);
        customOrderRepository.update(customOrder);

        //Assert
        assertEquals(ru.sivak.domain.order.custom.ApprovedState.class, customOrderRepository.find(customOrder.getId()).orElseThrow().getStateType());

        InStockOrder inStockOrder = new InStockOrder(Id.newId(), managerId, clientId, firstCar);
        inStockOrderRepository.create(inStockOrder);
        inStockOrder.approve();
        inStockOrder.requestPayment();
        inStockOrder.updateCar(secondCar);
        inStockOrderRepository.update(inStockOrder);

        //Assert
        assertEquals(WaitingPaymentState.class, inStockOrderRepository.find(inStockOrder.getId()).orElseThrow().getStateType());
    }

    @Test
    void test_drive_request_repository_creates_and_updates_request() {
        //Arrange
        Id clientId = id("00000000-0000-0000-0000-000000000002");
        Id firstCarId = id("00000000-0000-0000-0000-000000002001");
        Id secondCarId = id("00000000-0000-0000-0000-000000002002");

        TestDriveRequest request = TestDriveRequest.builder()
                .id(Id.newId())
                .clientId(clientId)
                .carId(firstCarId)
                .scheduledTime(LocalDate.of(2026, 4, 10))
                .build();
        testDriveRequestRepository.create(request);
        //Act
        request.updateCar(secondCarId);
        request.updateScheduledTime(LocalDate.of(2026, 4, 12));
        testDriveRequestRepository.update(request);

        TestDriveRequest actual = testDriveRequestRepository.find(request.getId()).orElseThrow();

        //Assert
        assertEquals(secondCarId, actual.getCarId());
        assertEquals(LocalDate.of(2026, 4, 12), actual.getScheduledTime());
    }

    private static Id id(String value) {
        return Id.of(UUID.fromString(value));
    }

    private static Money money(String amount) {
        return Money.of(new BigDecimal(amount));
    }

    private static Set<ModelName> models() {
        return Set.of(ModelName.of("Camry"), ModelName.of("Corolla"));
    }

    private static Body bodyFromSeed(String id, String bodyType) {
        return new Body(Id.of(UUID.fromString(id)), money("1000"), ComponentName.of("Body"), models(), BodyType.of(bodyType));
    }

    private static Brand brandFromSeed(String id, String name) {
        return new Brand(Id.of(UUID.fromString(id)), money("1500"), ComponentName.of("Brand"), models(), BrandName.of(name));
    }

    private static Color colorFromSeed(String id, String color) {
        return new Color(Id.of(UUID.fromString(id)), money("300"), ComponentName.of("Color"), models(), ColorValue.of(color));
    }

    private static Drive driveFromSeed(String id, String driveType) {
        return new Drive(Id.of(UUID.fromString(id)), money("700"), ComponentName.of("Drive"), models(), DriveType.of(driveType));
    }

    private static Engine engineFromSeed(String id, int power, int volume) {
        return new Engine(Id.of(UUID.fromString(id)), money("2400"), ComponentName.of("Engine"), models(), EnginePower.of(power), EngineVolume.of(volume));
    }

    private static Fuel fuelFromSeed(String id, String fuelType) {
        return new Fuel(Id.of(UUID.fromString(id)), money("200"), ComponentName.of("Fuel"), models(), FuelType.of(fuelType));
    }

    private static Model modelFromSeed(String id, String modelName) {
        return new Model(Id.of(UUID.fromString(id)), money("1200"), ComponentName.of("Model"), models(), ModelName.of(modelName));
    }

    private static Transmission transmissionFromSeed(String id, String type) {
        return new Transmission(Id.of(UUID.fromString(id)), money("1400"), ComponentName.of("Transmission"), models(), TransmissionType.of(type));
    }

    private static Steering steeringFromSeed(String id) {
        return new Steering(Id.of(UUID.fromString(id)), money("250"), ComponentName.of("Steering"), models());
    }

    private static Wheel wheelFromSeed(String id) {
        return new Wheel(Id.of(UUID.fromString(id)), money("450"), ComponentName.of("Wheel"), models());
    }

    private static Interior interiorFromSeed(String id) {
        return new Interior(Id.of(UUID.fromString(id)), money("900"), ComponentName.of("Interior"), models());
    }
}
