package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.BodyQuery;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.repositories.BodyRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBodyRepository implements BodyRepository {
    private final Map<Id, Body> storage = new HashMap<>();
    @Override
    public List<Body> query(BodyQuery query) {
        return storage.values().stream()
                .filter(body -> query.getMinPrice() == null ||
                        body.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(body -> query.getMaxPrice() == null ||
                        body.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(body -> query.getModelName() == null || body.getSuitableModels().contains(query.getModelName()))
                .collect(Collectors.toList());

    }

    @Override
    public void save(Body entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Body> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Body> findAll() {
        return new ArrayList<>(storage.values());
    }
}
