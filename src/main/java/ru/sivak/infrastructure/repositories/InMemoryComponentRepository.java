package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.ComponentQuery;
import ru.sivak.domain.entities.Component;
import ru.sivak.domain.repositories.ComponentRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryComponentRepository implements ComponentRepository {
    private final Map<Id, Component> storage = new HashMap<>();
    @Override
    public void save(Component entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Component> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Component> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Component> query(ComponentQuery query) {
        return storage.values().stream()
                .filter(component -> query.getType() == null ||
                        component.getType().equals(query.getType()))
                .filter(component -> query.getModelName() == null ||
                        component.isSuitableWith(query.getModelName()))
                .filter(component -> query.getName() == null ||
                        component.getName().equals(query.getName()))
                .filter(component -> query.getMinPrice() == null ||
                        component.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(component -> query.getMaxPrice() == null ||
                        component.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .collect(Collectors.toList());
    }
}
