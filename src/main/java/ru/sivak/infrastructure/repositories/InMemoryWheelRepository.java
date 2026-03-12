package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.WheelQuery;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.repositories.WheelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryWheelRepository implements WheelRepository {
    private final Map<Id, Wheel> storage = new HashMap<>();

    @Override
    public List<Wheel> query(WheelQuery query) {
        return storage.values().stream()
                .filter(w -> query.getMinPrice() == null || w.getPrice().getAmount()
                        .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(w -> query.getMaxPrice() == null || w.getPrice().getAmount()
                        .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(w -> query.getModelName() == null || w.getSuitableModels().contains(query.getModelName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Wheel entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Wheel> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Wheel> findAll() {
        return new ArrayList<>(storage.values());
    }
}
