package ru.sivak.application.query;

import lombok.Builder;
import lombok.Getter;
import ru.sivak.domain.order.custom.CustomOrderState;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;

@Getter
@Builder
public class CustomOrderQuery {
    private final Id clientId;
    private final Id managerId;
    private final Class<? extends CustomOrderState> stateType;
    private final Money minPrice;
    private final Money maxPrice;
}
