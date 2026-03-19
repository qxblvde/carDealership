package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.SteeringQuery;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.repositories.SteeringRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;

public class InMemorySteeringRepository implements SteeringRepository {

    private final Map<Id, Steering> storage = new HashMap<>();

    @Override
    public void save(Steering entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Steering> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Steering> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Steering> query(SteeringQuery query) {

        return storage.values().stream()
                .filter(s -> query.getMinPrice() == null ||
                        s.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(s -> query.getMaxPrice() == null ||
                        s.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(s -> query.getComponentName() == null || s.getComponentName().equals(query.getComponentName()))
                .filter(s -> query.getModelName() == null || s.isSuitableWith(query.getModelName()))
                .toList();
    }
}