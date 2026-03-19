package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.ModelQuery;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.repositories.ModelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryModelRepository implements ModelRepository {
    private final Map<Id, Model> storage = new HashMap<>();

    @Override
    public List<Model> query(ModelQuery query) {
        return storage.values().stream()
                .filter(m -> query.getMinPrice() == null ||
                        m.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(m -> query.getMaxPrice() == null ||
                        m.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(m -> query.getModelName() == null || m.getSuitableModels().contains(query.getModelName()))
                .filter(m -> query.getComponentName() == null || m.getComponentName().equals(query.getComponentName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Model entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Model> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Model> findAll() {
        return new ArrayList<>(storage.values());
    }
}
