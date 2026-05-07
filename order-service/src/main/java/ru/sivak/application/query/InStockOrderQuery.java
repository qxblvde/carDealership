package ru.sivak.application.query;

import lombok.Builder;
import lombok.Getter;
import ru.sivak.domain.order.inStock.InStockOrderState;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;
@Getter
@Builder(toBuilder = true)
public class InStockOrderQuery {
    private final Id clientId;
    private final Id managerId;
    private final Class<? extends InStockOrderState> stateType;
    private final Money minPrice;
    private final Money maxPrice;
}
