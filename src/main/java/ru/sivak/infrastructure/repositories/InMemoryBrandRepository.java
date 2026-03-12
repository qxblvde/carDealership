package ru.sivak.infrastructure.repositories;

import ru.sivak.application.query.BrandQuery;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.repositories.BrandRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBrandRepository implements BrandRepository {
    private final Map<Id, Brand> storage = new HashMap<>();

    @Override
    public List<Brand> query(BrandQuery query) {
        return storage.values().stream()
                .filter(brand -> query.getMinPrice() == null || brand.getPrice().getAmount()
                                .compareTo(query.getMinPrice().getAmount()) >= 0)
                .filter(brand -> query.getMaxPrice() == null || brand.getPrice().getAmount()
                                .compareTo(query.getMaxPrice().getAmount()) <= 0)
                .filter(brand -> query.getModelName() == null || brand.getSuitableModels().contains(query.getModelName()))
                .filter(brand -> query.getComponentName() == null || brand.getName().equals(query.getComponentName()))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Brand entity) {
        storage.put(entity.getId(), entity);
    }

    @Override
    public void delete(Id id) {
        storage.remove(id);
    }

    @Override
    public Optional<Brand> find(Id id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Brand> findAll() {
        return new ArrayList<>(storage.values());
    }
}
