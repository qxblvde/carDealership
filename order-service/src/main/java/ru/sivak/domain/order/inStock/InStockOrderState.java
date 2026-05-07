package ru.sivak.domain.order.inStock;

public interface InStockOrderState {
    boolean approve(InStockOrder order);
    boolean requestPayment(InStockOrder order);
    boolean pay(InStockOrder order);
    boolean markAsReady(InStockOrder order);
    boolean complete(InStockOrder order);
    boolean cancel(InStockOrder order);
}
