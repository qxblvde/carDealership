package ru.sivak.domain.repositories;

import ru.sivak.domain.valueObjects.Id;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {
    void create(T entity);

    void update(T entity);

    void delete(Id id);

    Optional<T> find(Id id);

    List<T> findAll();
}
