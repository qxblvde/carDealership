package ru.sivak.infrastructure.repositories.jpa;

import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.infrastructure.persistence.entity.CarModelEntity;
import ru.sivak.infrastructure.persistence.repository.CarModelEntityJpaRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public final class JpaRepositorySupport {
    private JpaRepositorySupport() {
    }

    public static Set<CarModelEntity> resolveModelEntities(
            CarModelEntityJpaRepository repository,
            Set<ModelName> modelNames) {
        if (modelNames == null || modelNames.isEmpty()) {
            return Collections.emptySet();
        }

        Set<String> rawModelNames = modelNames.stream()
                .map(ModelName::getName)
                .collect(Collectors.toSet());

        Set<CarModelEntity> entities = new HashSet<>(
                repository.findByModelNameInAndRemovedFalse(rawModelNames)
        );
        if (entities.size() != rawModelNames.size()) {
            throw new IllegalArgumentException("Some suitable models are missing in DB");
        }
        return entities;
    }
}
