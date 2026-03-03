package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.repositories.TestDriveRequestRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryTestDriveRequestRepository implements TestDriveRequestRepository {
    private final Map<Id, TestDriveRequest> storage = new HashMap<>();
    @Override
    public void save(TestDriveRequest entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<TestDriveRequest> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<TestDriveRequest> findAll() {
        return new ArrayList<>(storage.values());
    }
    @Override
    public List<TestDriveRequest> query(TestDriveRequestQuery query) {
        return storage.values().stream()
                .filter(request -> query.getClientId() == null ||
                        request.getClientId().equals(query.getClientId()))
                .filter(request -> query.getCarId() == null ||
                        request.getCarId().equals(query.getCarId()))
                .filter(request -> query.getFromDate() == null ||
                        !request.getScheduledTime().isBefore(query.getFromDate()))
                .filter(request -> query.getToDate() == null ||
                        !request.getScheduledTime().isAfter(query.getToDate()))
                .collect(Collectors.toList());

    }
}
