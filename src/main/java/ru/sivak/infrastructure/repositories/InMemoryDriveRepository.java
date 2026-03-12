package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.DriveQuery;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.repositories.DriveRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryDriveRepository implements DriveRepository {
    private final Map<Id, Drive> storage = new HashMap<>();

    @Override
    public List<Drive> query(DriveQuery query) {
        return storage.values().stream()
                .filter(d -> query.getDriveType() == null || d.getDriveType().equals(query.getDriveType()))
                .filter(d -> query.getMinPrice() == null ||
                        d.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(d -> query.getMaxPrice() == null ||
                        d.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(d -> query.getModelName() == null || d.getSuitableModels().contains(query.getModelName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Drive entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Drive> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Drive> findAll() {
        return new ArrayList<>(storage.values());
    }
}
