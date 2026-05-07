package ru.sivak.application.services;

import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;

import java.util.List;

public interface ICustomOrderService {
    OrderDto create(Id managerId, Id carId, Money price);
    List<OrderDto> query(CustomOrderQuery query);
    void approve(Id orderId);
    void requestPayment(Id orderId);
    void requestDelivery(Id orderId);
    void pay(Id orderId);
    void complete(Id orderId);
    void cancel(Id orderId);
    void markAsReady(Id orderId);

    CustomOrder get(Id id);
    OrderDto getDto(Id id);
    OrderDto update(Id orderId, Id newClientId, Id newCarId, Money newPrice);
}
