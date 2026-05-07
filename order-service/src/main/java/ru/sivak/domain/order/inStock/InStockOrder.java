package ru.sivak.domain.order.inStock;

import lombok.NonNull;
import ru.sivak.domain.order.BaseOrder;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;

public class InStockOrder extends BaseOrder {
    private InStockOrderState state;

    public InStockOrder(@NonNull Id id, @NonNull Id clientId, @NonNull Id managerId, @NonNull Id carId, @NonNull Money price) {
        super(id, clientId, managerId, carId, price);
        this.state = new CreatedState();
    }

    void changeState(@NonNull InStockOrderState state) {
        this.state = state;
    }

    public boolean approve() {
        return state.approve(this);
    }

    public boolean pay() {
        return state.pay(this);
    }

    public boolean markAsReady() {
        return state.markAsReady(this);
    }

    public boolean complete() {
        return state.complete(this);
    }

    public boolean requestPayment() {
        return state.requestPayment(this);
    }

    public boolean cancel() {
        return state.cancel(this);
    }

    public Class<? extends InStockOrderState> getStateType() {
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
