package domain;

import org.junit.jupiter.api.Test;
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
import ru.sivak.domain.order.custom.ApprovedState;
import ru.sivak.domain.order.custom.CanceledState;
import ru.sivak.domain.order.custom.CompletedState;
import ru.sivak.domain.order.custom.CreatedState;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.order.custom.PaidState;
import ru.sivak.domain.order.custom.ReadyForPickUpState;
import ru.sivak.domain.order.custom.WaitingDeliveryState;
import ru.sivak.domain.order.custom.WaitingPaymentState;
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

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomOrderStateTest {

    @Test
    void new_order_starts_in_created_state() {
        //Arrange
        CustomOrder order = order("15000");

        //Act
        Class<?> state = order.getStateType();

        //Assert
        assertEquals(CreatedState.class, state);
    }

    @Test
    void created_state_rejects_request_payment() {
        //Arrange
        CustomOrder order = order("15000");

        //Act
        boolean result = order.requestPayment();

        //Assert
        assertFalse(result);
    }

    @Test
    void created_state_rejects_pay() {
        //Arrange
        CustomOrder order = order("15000");

        //Act
        boolean result = order.pay();

        //Assert
        assertFalse(result);
    }

    @Test
    void created_state_approve_moves_to_approved() {
        //Arrange
        CustomOrder order = order("15000");

        //Act
        boolean result = order.approve();

        //Assert
        assertTrue(result);
        assertEquals(ApprovedState.class, order.getStateType());
    }

    @Test
    void created_state_cancel_moves_to_canceled() {
        //Arrange
        CustomOrder order = order("15000");

        //Act
        boolean result = order.cancel();

        //Assert
        assertTrue(result);
        assertEquals(CanceledState.class, order.getStateType());
    }

    @Test
    void approved_state_request_payment_moves_to_waiting_payment() {
        //Arrange
        CustomOrder order = order("15000");
        order.approve();

        //Act
        boolean result = order.requestPayment();

        //Assert
        assertTrue(result);
        assertEquals(WaitingPaymentState.class, order.getStateType());
    }

    @Test
    void approved_state_cancel_moves_to_canceled() {
        //Arrange
        CustomOrder order = order("15000");
        order.approve();

        //Act
        boolean result = order.cancel();

        //Assert
        assertTrue(result);
        assertEquals(CanceledState.class, order.getStateType());
    }

    @Test
    void waiting_payment_pay_moves_to_paid() {
        //Arrange
        CustomOrder order = order("15000");
        order.approve();
        order.requestPayment();

        //Act
        boolean result = order.pay();

        //Assert
        assertTrue(result);
        assertEquals(PaidState.class, order.getStateType());
    }

    @Test
    void waiting_payment_cancel_moves_to_canceled() {
        //Arrange
        CustomOrder order = order("15000");
        order.approve();
        order.requestPayment();

        //Act
        boolean result = order.cancel();

        //Assert
        assertTrue(result);
        assertEquals(CanceledState.class, order.getStateType());
    }

    @Test
    void paid_flow_reaches_completed_state() {
        //Arrange
        CustomOrder order = order("15000");
        order.approve();
        order.requestPayment();
        order.pay();

        //Act
        boolean deliveryRequested = order.requestDelivery();
        Class<?> waitingDeliveryState = order.getStateType();
        boolean markedReady = order.markAsReady();
        Class<?> readyState = order.getStateType();
        boolean completed = order.complete();

        //Assert
        assertTrue(deliveryRequested);
        assertEquals(WaitingDeliveryState.class, waitingDeliveryState);
        assertTrue(markedReady);
        assertEquals(ReadyForPickUpState.class, readyState);
        assertTrue(completed);
        assertEquals(CompletedState.class, order.getStateType());
    }

    @Test
    void update_methods_change_client_and_price() {
        //Arrange
        CustomOrder order = order("15000");
        Id newClientId = Id.newId();
        Car newCar = car("21000");

        //Act
        order.updateClient(newClientId);
        order.updateCar(newCar);

        //Assert
        assertEquals(newClientId, order.getClientId());
        assertEquals(Money.of(new BigDecimal("21000")), order.getPrice());
    }

    private static CustomOrder order(String price) {
        return new CustomOrder(Id.newId(), Id.newId(), Id.newId(), car(price));
    }

    private static Money money(String amount) {
        return Money.of(new BigDecimal(amount));
    }

    private static Set<ModelName> models() {
        return Set.of(ModelName.of("Camry"), ModelName.of("Corolla"));
    }

    private static Car car(String price) {
        Set<ModelName> suitableModels = models();
        return Car.builder()
                .id(Id.newId())
                .bodyType(new Body(Id.newId(), money("1000"), ComponentName.of("Body"), suitableModels, BodyType.of("Sedan")))
                .brandName(new Brand(Id.newId(), money("1500"), ComponentName.of("Brand"), suitableModels, BrandName.of("Toyota")))
                .color(new Color(Id.newId(), money("300"), ComponentName.of("Color"), suitableModels, ColorValue.of("Black")))
                .driveType(new Drive(Id.newId(), money("700"), ComponentName.of("Drive"), suitableModels, DriveType.of("example1")))
                .engine(new Engine(Id.newId(), money("2400"), ComponentName.of("Engine"), suitableModels, EnginePower.of(220), EngineVolume.of(2000)))
                .fuel(new Fuel(Id.newId(), money("200"), ComponentName.of("Fuel"), suitableModels, FuelType.of("Petrol")))
                .model(new Model(Id.newId(), money("1200"), ComponentName.of("Model"), suitableModels, ModelName.of("Camry")))
                .price(money(price))
                .transmission(new Transmission(Id.newId(), money("1400"), ComponentName.of("Transmission"), suitableModels, TransmissionType.of("example1")))
                .steering(new Steering(Id.newId(), money("250"), ComponentName.of("Steering"), suitableModels))
                .wheel(new Wheel(Id.newId(), money("450"), ComponentName.of("Wheel"), suitableModels))
                .interior(new Interior(Id.newId(), money("900"), ComponentName.of("Interior"), suitableModels))
                .build();
    }
}
