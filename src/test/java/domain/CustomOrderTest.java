package domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.order.custom.*;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CustomOrderTest {
    private CustomOrder order;

    private Car buildCar() {
        return Car.builder()
                .id(Id.newId()).
                bodyType(BodyType.of("Sedan"))
                .brandName(BrandName.of("Toyota"))
                .color(Color.of("White"))
                .driveType(DriveType.of("abc"))
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
        order = new CustomOrder(Id.newId(), Id.newId(), Id.newId(), buildCar());
    }

    @Test
    void createdState_isFirst() {
        assertEquals(CreatedState.class, order.getStateType());
    }

    @Test
    void approve_fromCreated_toApproved() {
        assertTrue(order.approve());
        assertEquals(ApprovedState.class, order.getStateType());
    }

    @Test
    void cancel_fromCreated_toCanceled() {
        assertTrue(order.cancel());
        assertEquals(CanceledState.class, order.getStateType());
    }

    @Test
    void approve_fromCanceled_returnsFalse() {
        order.cancel();
        assertFalse(order.approve());
        assertEquals(CanceledState.class, order.getStateType());
    }

    @Test
    void requestPayment_fromApproved_toWaitingPayment() {
        order.approve();
        assertTrue(order.requestPayment());
        assertEquals(WaitingPaymentState.class, order.getStateType());
    }

    @Test
    void cancel_fromApproved_toCanceled() {
        order.approve();
        assertTrue(order.cancel());
        assertEquals(CanceledState.class, order.getStateType());
    }

    @Test
    void pay_fromWaitingPayment_toPaid() {
        order.approve();
        order.requestPayment();
        assertTrue(order.pay());
        assertEquals(PaidState.class, order.getStateType());
    }

    @Test
    void cancel_fromWaitingPayment_toCanceled() {
        order.approve();
        order.requestPayment();
        assertTrue(order.cancel());
        assertEquals(CanceledState.class, order.getStateType());
    }

    @Test
    void markAsReady_fromPaid_toReadyForPickUp() {
        order.approve();
        order.requestPayment();
        order.pay();
        assertTrue(order.requestDelivery());
        assertEquals(WaitingDeliveryState.class, order.getStateType());
    }

    @Test
    void cancel_fromCompleted_returnsFalse() {
        order.approve();
        order.requestPayment();
        order.pay();
        order.markAsReady();
        order.complete();
        assertFalse(order.cancel());
    }

    @Test
    void pay_fromCreated_returnsFalse() {
        assertFalse(order.pay());
    }

    @Test
    void markAsReady_fromCreated_returnsFalse() {
        assertFalse(order.markAsReady());
    }

    @Test
    void complete_fromCreated_returnsFalse() {
        assertFalse(order.complete());
    }

    @Test
    void requestPayment_fromCreated_returnsFalse() {
        assertFalse(order.requestPayment());
    }
}
