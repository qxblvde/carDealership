package ru.sivak.domain.order.inStock;

public class CreatedState implements InStockOrderState {
    @Override
    public boolean approve(InStockOrder order) {
        order.changeState(new ApprovedState());
        return true;
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
        return false;
    }

    @Override
    public boolean cancel(InStockOrder order) {
        order.changeState(new CanceledState());
        return true;
    }
}
