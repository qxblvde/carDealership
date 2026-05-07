package ru.sivak.domain.order.custom;

import lombok.Getter;
import lombok.NonNull;
import ru.sivak.domain.order.BaseOrder;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;

@Getter
public class CustomOrder extends BaseOrder {
    private CustomOrderState state;

    public CustomOrder(@NonNull Id id, @NonNull Id managerId, @NonNull Id clientId, @NonNull Id carId, @NonNull Money price) {
        super(id, managerId, clientId, carId, price);
        this.state = new CreatedState();
    }

    void changeState(@NonNull CustomOrderState state) {
        this.state = state;
    }

    public boolean approve() {
        return state.approve(this);
    }
    public boolean requestPayment() {
        return state.requestPayment(this);
    }
    public boolean pay() {
        return state.pay(this);
    }
    public boolean requestDelivery() {
        return state.requestDelivery(this);
    }
    public boolean markAsReady() {
        return state.markAsReady(this);
    }
    public boolean complete() {
        return state.complete(this);
    }
    public boolean cancel() {
        return state.cancel(this);
    }

    public Class<? extends CustomOrderState> getStateType() {
        return state.getClass();
    }

    public void updateClient(@NonNull Id newClientId) {
        setClientId(newClientId);
    }

    public void updateCar(@NonNull Id newCarId, @NonNull Money newPrice) {
        setCarId(newCarId);
        setPrice(newPrice);
    }
}
