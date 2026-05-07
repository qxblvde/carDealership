package ru.sivak.domain.repositories;

import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.domain.order.inStock.InStockOrder;

import java.util.List;

public interface InStockOrderRepository extends Repository<InStockOrder> {
    List<InStockOrder> query(InStockOrderQuery query);
}
