package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.repositories.CustomOrderRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryCustomOrderRepository implements CustomOrderRepository {
    private final Map<Id, CustomOrder> storage = new HashMap<>();
    @Override
    public void save(CustomOrder entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<CustomOrder> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<CustomOrder> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<CustomOrder> query(CustomOrderQuery query) {
        return storage.values().stream()
                .filter(order -> query.getClientId() == null ||
                        order.getClientId().equals(query.getClientId()))
                .filter(order -> query.getManagerId() == null ||
                        order.getManagerId().equals(query.getManagerId()))
                .filter(order -> query.getStateType() == null ||
                        order.getStateType().equals(query.getStateType()))
                .filter(order -> query.getMinPrice() == null ||
                        order.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(order -> query.getMaxPrice() == null ||
                        order.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .collect(Collectors.toList());
    }
}
