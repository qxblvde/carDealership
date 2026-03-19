package ru.sivak.domain.order.inStock;

public class CanceledState implements InStockOrderState{
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
        return false;
    }

    @Override
    public boolean cancel(InStockOrder order) {
        return false;
    }
}
