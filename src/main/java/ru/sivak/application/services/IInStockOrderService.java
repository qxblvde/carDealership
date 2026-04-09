package ru.sivak.application.services;

import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IInStockOrderService {
    OrderDto create(Id managerId, Id clientId, Id carId);
    List<OrderDto> query(InStockOrderQuery query);
    void approve(Id orderId);
    void requestPayment(Id orderId);
    void pay(Id orderId);
    void complete(Id orderId);
    void cancel(Id orderId);
    void markAsReady(Id orderId);

    InStockOrder get(Id id);
    OrderDto getDto(Id id);
    OrderDto update(Id orderId, Id newClientId, Id newCarId);
}
