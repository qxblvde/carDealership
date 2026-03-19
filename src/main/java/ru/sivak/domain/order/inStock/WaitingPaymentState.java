package ru.sivak.domain.order.inStock;

public class WaitingPaymentState implements InStockOrderState{
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
        order.changeState(new PaidState());
        return true;
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
