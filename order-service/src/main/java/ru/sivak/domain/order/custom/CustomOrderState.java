package ru.sivak.domain.order.custom;

public interface CustomOrderState {
    boolean approve(CustomOrder order);
    boolean requestPayment(CustomOrder order);
    boolean pay(CustomOrder order);
    boolean markAsReady(CustomOrder order);
    boolean complete(CustomOrder order);
    boolean cancel(CustomOrder order);
    boolean requestDelivery(CustomOrder order);
}
