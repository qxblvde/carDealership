package application;

import org.junit.jupiter.api.Test;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.mappers.OrderMapperImpl;
import ru.sivak.application.mappers.TestDriveMapperImpl;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.application.servicesImpl.CustomOrderService;
import ru.sivak.application.servicesImpl.InStockOrderService;
import ru.sivak.application.servicesImpl.TestDriveRequestService;
import ru.sivak.domain.entities.*;
import ru.sivak.domain.order.custom.CompletedState;
import ru.sivak.domain.order.custom.CreatedState;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.order.inStock.WaitingPaymentState;
import ru.sivak.domain.repositories.*;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ApplicationOrderServicesTest {

    @Test
    void custom_order_service_creates_order() {
        //Arrange
        CustomOrderRepository orderRepository = mock(CustomOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        CustomOrderService service = new CustomOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id managerId = Id.newId();
        Id clientId = Id.newId();
        Car car = car("Camry", "15000", models());
        when(carRepository.find(car.getId())).thenReturn(Optional.of(car));

        //Act
        OrderDto created = service.create(managerId, clientId, car.getId());

        //Assert
        assertEquals(clientId, created.clientId());
        assertEquals(managerId, created.managerId());
        assertEquals(CreatedState.class, created.stateType());
        verify(orderRepository).create(any(CustomOrder.class));
    }

    @Test
    void custom_order_service_updates_client_and_total() {
        //Arrange
        CustomOrderRepository orderRepository = mock(CustomOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        CustomOrderService service = new CustomOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id orderId = Id.newId();
        Id managerId = Id.newId();
        Id clientId = Id.newId();
        Id newClientId = Id.newId();
        Car firstCar = car("Camry", "15000", models());
        Car secondCar = car("Corolla", "21000", models());
        CustomOrder order = new CustomOrder(orderId, managerId, clientId, firstCar);

        //Act
        when(orderRepository.find(orderId)).thenReturn(Optional.of(order));
        when(carRepository.find(secondCar.getId())).thenReturn(Optional.of(secondCar));

        OrderDto updated = service.update(orderId, newClientId, secondCar.getId());

        //Assert
        assertEquals(newClientId, updated.clientId());
        assertEquals(secondCar.getPrice(), updated.total());
        verify(orderRepository).update(order);
    }

    @Test
    void custom_order_service_queries_by_client_and_state() {
        //Arrange
        CustomOrderRepository orderRepository = mock(CustomOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        CustomOrderService service = new CustomOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id managerId = Id.newId();
        Id clientId = Id.newId();
        CustomOrder order = new CustomOrder(Id.newId(), managerId, clientId, car("Camry", "15000", models()));
        when(orderRepository.query(any(CustomOrderQuery.class))).thenReturn(List.of(order));

        //Act
        List<OrderDto> result = service.query(CustomOrderQuery.builder().clientId(clientId).stateType(CreatedState.class).build());

        //Assert
        assertEquals(1, result.size());
        assertEquals(clientId, result.getFirst().clientId());
        assertEquals(CreatedState.class, result.getFirst().stateType());
    }

    @Test
    void custom_order_service_completes_order() {
        //Arrange
        CustomOrderRepository orderRepository = mock(CustomOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        CustomOrderService service = new CustomOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id orderId = Id.newId();
        CustomOrder order = new CustomOrder(orderId, Id.newId(), Id.newId(), car("Camry", "15000", models()));
        when(orderRepository.find(orderId)).thenReturn(Optional.of(order));

        //Act
        service.approve(orderId);
        service.requestPayment(orderId);
        service.pay(orderId);
        service.requestDelivery(orderId);
        service.markAsReady(orderId);
        service.complete(orderId);

        //Assert
        assertEquals(CompletedState.class, order.getStateType());
        verify(orderRepository, times(6)).update(order);
    }

    @Test
    void custom_order_service_throws_for_missing_car_and_order() {
        //Arrange
        CustomOrderRepository orderRepository = mock(CustomOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        CustomOrderService service = new CustomOrderService(orderRepository, carRepository, new OrderMapperImpl());

        //Act
        when(carRepository.find(any(Id.class))).thenReturn(Optional.empty());
        when(orderRepository.find(any(Id.class))).thenReturn(Optional.empty());

        //Assert
        assertThrows(IllegalArgumentException.class, () -> service.create(Id.newId(), Id.newId(), Id.newId()));
        assertThrows(IllegalArgumentException.class, () -> service.get(Id.newId()));
    }

    @Test
    void custom_order_service_throws_for_invalid_transition() {
        //Arrange
        CustomOrderRepository orderRepository = mock(CustomOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        CustomOrderService service = new CustomOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id orderId = Id.newId();
        CustomOrder order = new CustomOrder(orderId, Id.newId(), Id.newId(), car("Camry", "15000", models()));

        //Act
        when(orderRepository.find(orderId)).thenReturn(Optional.of(order));

        //Assert
        assertThrows(IllegalArgumentException.class, () -> service.complete(orderId));
        verify(orderRepository, never()).update(order);
    }

    @Test
    void in_stock_order_service_creates_order() {
        //Arrange
        InStockOrderRepository orderRepository = mock(InStockOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        InStockOrderService service = new InStockOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id managerId = Id.newId();
        Id clientId = Id.newId();
        Car car = car("Camry", "15000", models());
        when(carRepository.find(car.getId())).thenReturn(Optional.of(car));

        //Act
        OrderDto created = service.create(managerId, clientId, car.getId());

        //Assert
        assertEquals(clientId, created.clientId());
        assertEquals(managerId, created.managerId());
        assertEquals(ru.sivak.domain.order.inStock.CreatedState.class, created.stateType());
        verify(orderRepository).create(any(InStockOrder.class));
    }

    @Test
    void in_stock_order_service_updates_client_and_total() {
        //Arrange
        InStockOrderRepository orderRepository = mock(InStockOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        InStockOrderService service = new InStockOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id orderId = Id.newId();
        Id managerId = Id.newId();
        Id clientId = Id.newId();
        Id newClientId = Id.newId();
        Car firstCar = car("Camry", "15000", models());
        Car secondCar = car("Corolla", "21000", models());
        InStockOrder order = new InStockOrder(orderId, managerId, clientId, firstCar);

        //Act
        when(orderRepository.find(orderId)).thenReturn(Optional.of(order));
        when(carRepository.find(secondCar.getId())).thenReturn(Optional.of(secondCar));

        OrderDto updated = service.update(orderId, newClientId, secondCar.getId());

        //Assert
        assertEquals(newClientId, updated.clientId());
        assertEquals(secondCar.getPrice(), updated.total());
        verify(orderRepository).update(order);
    }

    @Test
    void in_stock_order_service_queries_by_client_and_state() {
        //Arrange
        InStockOrderRepository orderRepository = mock(InStockOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        InStockOrderService service = new InStockOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id managerId = Id.newId();
        Id clientId = Id.newId();
        InStockOrder order = new InStockOrder(Id.newId(), managerId, clientId, car("Camry", "15000", models()));
        //Act
        order.approve();
        order.requestPayment();
        when(orderRepository.query(any(InStockOrderQuery.class))).thenReturn(List.of(order));

        List<OrderDto> result = service.query(
                InStockOrderQuery.builder()
                        .clientId(clientId)
                        .stateType(WaitingPaymentState.class)
                        .build()
        );
        //Assert
        assertEquals(1, result.size());
        assertEquals(clientId, result.getFirst().clientId());
        assertEquals(WaitingPaymentState.class, result.getFirst().stateType());
    }

    @Test
    void in_stock_order_service_completes_order() {
        //Arrange
        InStockOrderRepository orderRepository = mock(InStockOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        InStockOrderService service = new InStockOrderService(orderRepository, carRepository, new OrderMapperImpl());

        Id orderId = Id.newId();
        InStockOrder order = new InStockOrder(orderId, Id.newId(), Id.newId(), car("Camry", "15000", models()));
        when(orderRepository.find(orderId)).thenReturn(Optional.of(order));

        //Act
        service.approve(orderId);
        service.requestPayment(orderId);
        service.pay(orderId);
        service.markAsReady(orderId);
        service.complete(orderId);

        //Assert
        assertEquals(ru.sivak.domain.order.inStock.CompletedState.class, order.getStateType());
        verify(orderRepository, times(5)).update(order);
    }

    @Test
    void in_stock_order_service_throws_for_missing_car_and_order() {
        //Arrange
        InStockOrderRepository orderRepository = mock(InStockOrderRepository.class);
        CarRepository carRepository = mock(CarRepository.class);
        InStockOrderService service = new InStockOrderService(orderRepository, carRepository, new OrderMapperImpl());

        //Act
        when(carRepository.find(any(Id.class))).thenReturn(Optional.empty());
        when(orderRepository.find(any(Id.class))).thenReturn(Optional.empty());

        //Assert
        assertThrows(IllegalArgumentException.class, () -> service.create(Id.newId(), Id.newId(), Id.newId()));
        assertThrows(IllegalArgumentException.class, () -> service.get(Id.newId()));
    }

    @Test
    void test_drive_request_service_creates_updates_and_gets_request() {
        //Arrange
        TestDriveRequestRepository repository = mock(TestDriveRequestRepository.class);
        TestDriveRequestService service = new TestDriveRequestService(repository, new TestDriveMapperImpl());

        Id requestId = Id.newId();
        Id clientId = Id.newId();
        Id carId = Id.newId();
        LocalDate date = LocalDate.of(2026, 3, 25);

        //Act
        TestDriveRequest request = TestDriveRequest.builder()
                .id(requestId)
                .clientId(clientId)
                .carId(carId)
                .scheduledTime(date)
                .build();

        when(repository.find(requestId)).thenReturn(Optional.of(request), Optional.of(request));

        service.update(requestId, Id.newId(), Id.newId(), LocalDate.of(2026, 3, 26));
        Id actualId = service.get(requestId).id();

        //Assert
        assertEquals(requestId, actualId);
        verify(repository).update(request);
    }

    @Test
    void test_drive_request_service_queries_and_deletes_request() {
        //Arrange
        TestDriveRequestRepository repository = mock(TestDriveRequestRepository.class);
        TestDriveRequestService service = new TestDriveRequestService(repository, new TestDriveMapperImpl());

        Id requestId = Id.newId();
        TestDriveRequest request = TestDriveRequest.builder()
                .id(requestId)
                .clientId(Id.newId())
                .carId(Id.newId())
                .scheduledTime(LocalDate.of(2026, 3, 25))
                .build();

        when(repository.query(any(TestDriveRequestQuery.class))).thenReturn(List.of(request));

        //Act
        int size = service.query(TestDriveRequestQuery.builder().fromDate(LocalDate.of(2026, 3, 24)).toDate(LocalDate.of(2026, 3, 26)).build()).size();
        service.delete(requestId);

        //Assert
        assertEquals(1, size);
        verify(repository).delete(requestId);
    }

    @Test
    void test_drive_request_service_throws_for_missing_request() {
        //Arrange
        TestDriveRequestRepository repository = mock(TestDriveRequestRepository.class);
        TestDriveRequestService service = new TestDriveRequestService(repository, new TestDriveMapperImpl());

        //Act
        when(repository.find(any(Id.class))).thenReturn(Optional.empty());

        //Assert
        assertThrows(IllegalArgumentException.class, () -> service.get(Id.newId()));
        assertThrows(IllegalArgumentException.class, () -> service.update(Id.newId(), Id.newId(), Id.newId(), LocalDate.of(2026, 3, 26)));
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
