package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.EngineQuery;
import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.repositories.EngineRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryEngineRepository implements EngineRepository {
    private final Map<Id, Engine> storage = new HashMap<>();

    @Override
    public List<Engine> query(EngineQuery query) {
        return storage.values().stream()
                .filter(e -> query.getPower() == null || e.getPower().equals(query.getPower()))
                .filter(e -> query.getVolume() == null || e.getVolume().equals(query.getVolume()))
                .filter(e -> query.getMinPrice() == null ||
                        e.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(e -> query.getMaxPrice() == null ||
                        e.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(e -> query.getModelName() == null || e.getSuitableModels().contains(query.getModelName()))
                .filter(e-> query.getComponentName() == null || e.getComponentName().equals(query.getComponentName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Engine entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Engine> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Engine> findAll() {
        return new ArrayList<>(storage.values());
    }
}
