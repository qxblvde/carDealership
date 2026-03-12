package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.ColorQuery;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.repositories.ColorRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryColorRepository implements ColorRepository {
    private final Map<Id, Color> storage = new HashMap<>();
    @Override
    public List<Color> query(ColorQuery query) {
        return storage.values().stream()
                .filter(c -> query.getColor() == null || c.getColor().equals(query.getColor()))
                .filter(c -> query.getModelName() == null || c.getSuitableModels().contains(query.getModelName()))
                .filter(c -> query.getComponentName() == null || c.getComponentName().equals(query.getComponentName()))
                .filter(c -> query.getMinPrice() == null || c.getPrice().getAmount()
                        .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(c -> query.getMaxPrice() == null || c.getPrice().getAmount()
                        .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Color entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Color> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Color> findAll() {
        return new ArrayList<>(storage.values());
    }
}
