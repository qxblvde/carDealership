package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.FuelQuery;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.repositories.FuelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryFuelRepository implements FuelRepository {
    private final Map<Id, Fuel> storage = new HashMap<>();
    @Override
    public List<Fuel> query(FuelQuery query) {
        return storage.values().stream()
                .filter(f -> query.getFuelType() == null || f.getFuelType().equals(query.getFuelType()))
                .filter(f -> query.getMinPrice() == null ||
                        f.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(f -> query.getMaxPrice() == null ||
                        f.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(f -> query.getModelName() == null || f.getSuitableModels().contains(query.getModelName()))
                .filter(f -> query.getComponentName() == null || f.getComponentName().equals(query.getComponentName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Fuel entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Fuel> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Fuel> findAll() {
        return new ArrayList<>(storage.values());
    }
}
