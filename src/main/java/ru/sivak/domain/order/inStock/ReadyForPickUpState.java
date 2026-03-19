package ru.sivak.domain.order.inStock;

public class ReadyForPickUpState implements InStockOrderState {
    @Override
    public boolean approve(InStockOrder order) {
        return false;
    }

    @Override
    public boolean requestPayment(InStockOrder order) {
        return false;
    }

    @Override
    public boolean pay(InStockOrder order) {
        return false;
    }

    @Override
    public boolean markAsReady(InStockOrder order) {
        return false;
    }

    @Override
    public boolean complete(InStockOrder order) {
        order.changeState(new CompletedState());
        return true;
    }

    @Override
    public boolean cancel(InStockOrder order) {
        return false;
    }
}
