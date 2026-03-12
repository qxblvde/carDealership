package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.repositories.TransmissonRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTransmissonRepository implements TransmissonRepository {
    private final Map<Id, Transmission> storage = new HashMap<>();

    @Override
    public List<Transmission> query(TransmissionQuery query) {
        return storage.values().stream()
                .filter(t -> query.getType() == null || t.getTransmissionType().equals(query.getType()))
                .filter(t -> query.getMinPrice() == null ||
                        t.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(t -> query.getMaxPrice() == null ||
                        t.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(t -> query.getModelName() == null || t.getSuitableModels().contains(query.getModelName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Transmission entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Transmission> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Transmission> findAll() {
        return new ArrayList<>(storage.values());
    }
}
