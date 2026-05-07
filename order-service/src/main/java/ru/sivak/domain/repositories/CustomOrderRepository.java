package ru.sivak.domain.repositories;

import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.domain.order.custom.CustomOrder;

import java.util.List;

public interface CustomOrderRepository extends Repository<CustomOrder> {
    List<CustomOrder> query(CustomOrderQuery query);
}
