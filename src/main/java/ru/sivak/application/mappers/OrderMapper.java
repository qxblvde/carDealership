package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.domain.order.BaseOrder;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.order.inStock.InStockOrder;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "total", source = "price")
    @Mapping(target = "stateType", expression = "java(resolveStateType(order))")
    OrderDto map(BaseOrder order);

    default Class<?> resolveStateType(BaseOrder order) {
        if (order instanceof CustomOrder customOrder) {
            return customOrder.getStateType();
        }
        if (order instanceof InStockOrder inStockOrder) {
            return inStockOrder.getStateType();
        }
        return order.getClass();
    }
}
