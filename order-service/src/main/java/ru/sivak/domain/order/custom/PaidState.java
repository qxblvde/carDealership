package ru.sivak.domain.order.custom;

public class PaidState implements CustomOrderState{
    @Override
    public boolean approve(CustomOrder order) {
        return false;
    }

    @Override
    public boolean requestPayment(CustomOrder order) {
        return false;
    }

    @Override
    public boolean pay(CustomOrder order) {
        return false;
    }

    @Override
    public boolean requestDelivery(CustomOrder order) {
        order.changeState(new WaitingDeliveryState());
        return true;
    }

    @Override
    public boolean markAsReady(CustomOrder order) {
        return false;
    }

    @Override
    public boolean complete(CustomOrder order) {
        return false;
    }

    @Override
    public boolean cancel(CustomOrder order) {
        return false;
    }
}
