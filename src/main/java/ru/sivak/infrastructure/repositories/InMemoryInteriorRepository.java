package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.InteriorQuery;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.repositories.InteriorRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;

public class InMemoryInteriorRepository implements InteriorRepository {

    private final Map<Id, Interior> storage = new HashMap<>();

    @Override
    public void save(Interior entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Interior> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Interior> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public List<Interior> query(InteriorQuery query) {

        return storage.values().stream()
                .filter(i -> query.getMinPrice() == null ||
                        i.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(i -> query.getMaxPrice() == null ||
                        i.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(i -> query.getComponentName() == null || i.getComponentName().equals(query.getComponentName()))
                .filter(i -> query.getModelName() == null || i.isSuitableWith(query.getModelName()))
                .toList();
    }
}