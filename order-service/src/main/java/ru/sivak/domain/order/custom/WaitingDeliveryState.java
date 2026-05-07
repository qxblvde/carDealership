package ru.sivak.domain.order.custom;

public class WaitingDeliveryState implements CustomOrderState{
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
        return false;
    }

    @Override
    public boolean markAsReady(CustomOrder order) {
        order.changeState(new ReadyForPickUpState());
        return true;
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
