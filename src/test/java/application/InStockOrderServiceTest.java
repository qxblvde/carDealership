package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.application.servicesImpl.InStockOrderService;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.order.inStock.*;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.repositories.InStockOrderRepository;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InStockOrderServiceTest {
    @Mock
    private InStockOrderRepository inStockOrderRepository;
    @Mock
    private CarRepository carRepository;
    private InStockOrderService service;

    private Car buildCar(Id id) {
        return Car.builder()
                .id(id).bodyType(BodyType.of("Sedan"))
                .brandName(BrandName.of("Toyota"))
                .color(Color.of("White"))
                .driveType(DriveType.of("abs"))
                .enginePower(EnginePower.of(200))
                .engineVolume(EngineVolume.of(2000))
                .fuelType(FuelType.of("Petrol"))
                .modelName(ModelName.of("Camry"))
                .price(Money.of(BigDecimal.valueOf(20000)))
                .transmissionType(TransmissionType.of("Auto"))
                .build();
    }

    @BeforeEach
    void setUp() {
        service = new InStockOrderService(inStockOrderRepository, carRepository);
    }

    @Test
    void create_returnsDto() {
        Id carId = Id.newId();
        when(carRepository.find(carId)).thenReturn(Optional.of(buildCar(carId)));
        OrderDto dto = service.create(Id.newId(), carId);
        verify(inStockOrderRepository).save(any(InStockOrder.class));
        assertNotNull(dto);
    }

    @Test
    void create_throws_ifCarNotFound() {
        Id carId = Id.newId();
        when(carRepository.find(carId)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.create(Id.newId(), carId));
    }

    @Test
    void approve_toApproved() {
        Id orderId = Id.newId();
        InStockOrder order = new InStockOrder(orderId, Id.newId(), Id.newId(), buildCar(Id.newId()));
        when(inStockOrderRepository.find(orderId)).thenReturn(Optional.of(order));
        service.approve(orderId);
        assertEquals(ApprovedState.class, order.getStateType());
    }

    @Test
    void requestPayment_toWaitingPayment() {
        Id orderId = Id.newId();
        InStockOrder order = new InStockOrder(orderId, Id.newId(), Id.newId(), buildCar(Id.newId()));
        order.approve();
        when(inStockOrderRepository.find(orderId)).thenReturn(Optional.of(order));
        service.requestPayment(orderId);
        assertEquals(WaitingPaymentState.class, order.getStateType());
    }

    @Test
    void pay_toPaid() {
        Id orderId = Id.newId();
        InStockOrder order = new InStockOrder(orderId, Id.newId(), Id.newId(), buildCar(Id.newId()));
        order.approve();
        order.requestPayment();
        when(inStockOrderRepository.find(orderId)).thenReturn(Optional.of(order));
        service.pay(orderId);
        assertEquals(PaidState.class, order.getStateType());
    }

    @Test
    void markAsReady_toReadyForPickUp() {
        Id orderId = Id.newId();
        InStockOrder order = new InStockOrder(orderId, Id.newId(), Id.newId(), buildCar(Id.newId()));
        order.approve();
        order.requestPayment();
        order.pay();
        when(inStockOrderRepository.find(orderId)).thenReturn(Optional.of(order));
        service.markAsReady(orderId);
        assertEquals(ReadyForPickUpState.class, order.getStateType());
    }

    @Test
    void complete_toCompleted() {
        Id orderId = Id.newId();
        InStockOrder order = new InStockOrder(orderId, Id.newId(), Id.newId(), buildCar(Id.newId()));
        order.approve();
        order.requestPayment();
        order.pay();
        order.markAsReady();
        when(inStockOrderRepository.find(orderId)).thenReturn(Optional.of(order));
        service.complete(orderId);
        assertEquals(CompletedState.class, order.getStateType());
    }

    @Test
    void cancel_toCanceled() {
        Id orderId = Id.newId();
        InStockOrder order = new InStockOrder(orderId, Id.newId(), Id.newId(), buildCar(Id.newId()));
        when(inStockOrderRepository.find(orderId)).thenReturn(Optional.of(order));
        service.cancel(orderId);
        assertEquals(CanceledState.class, order.getStateType());
    }

    @Test
    void query_returnsDto() {
        InStockOrder order = new InStockOrder(Id.newId(), Id.newId(), Id.newId(), buildCar(Id.newId()));
        InStockOrderQuery query = InStockOrderQuery.builder().build();
        when(inStockOrderRepository.query(query)).thenReturn(List.of(order));
        assertEquals(1, service.query(query).size());
    }

    @Test
    void get_throws_ifNotFound() {
        Id id = Id.newId();
        when(inStockOrderRepository.find(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.get(id));
    }
}
