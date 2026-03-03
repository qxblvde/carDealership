package ru.sivak.application.mappers;

import ru.sivak.application.dto.OrderDto;
import ru.sivak.domain.order.BaseOrder;

public final class OrderMapper {
    private OrderMapper() {}

    public static OrderDto toDto(BaseOrder order) {
        return new OrderDto(
                order.getId(),
                order.getClientId(),
                order.getManagerId(),
                order.getPrice(),
                order.getClass()
        );
    }
}
