package ru.sivak.integration.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.domain.order.custom.CustomOrderState;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;
import ru.sivak.integration.rest.dto.CreateOrderRequest;
import ru.sivak.integration.rest.dto.UpdateOrderRequest;

import java.math.BigDecimal;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomOrderRequestMapper {

    @Mapping(target = "managerId", source = "request.managerId")
    @Mapping(target = "clientId", source = "request.clientId")
    @Mapping(target = "carId", source = "request.carId")
    CreateCommand toCreateCommand(CreateOrderRequest request);

    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "clientId", source = "request.clientId")
    @Mapping(target = "carId", source = "request.carId")
    UpdateCommand toUpdateCommand(UUID orderId, UpdateOrderRequest request);

    @Mapping(target = "clientId", source = "clientId")
    @Mapping(target = "managerId", source = "managerId")
    @Mapping(target = "stateType", source = "state")
    @Mapping(target = "minPrice", source = "minPrice")
    @Mapping(target = "maxPrice", source = "maxPrice")
    CustomOrderQuery toQuery(UUID clientId, UUID managerId, String state, BigDecimal minPrice, BigDecimal maxPrice);

    default Id toId(UUID value) {
        return value == null ? null : Id.of(value);
    }

    default Money mapMoney(BigDecimal value) {
        return value == null ? null : Money.of(value);
    }

    default Class<? extends CustomOrderState> mapStateType(String value) {
        return OrderStateMapper.parseCustomState(value);
    }

    record CreateCommand(Id managerId, Id clientId, Id carId) {
    }

    record UpdateCommand(Id orderId, Id clientId, Id carId) {
    }
}
