package infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sivak.application.query.ComponentQuery;
import ru.sivak.domain.entities.Component;
import ru.sivak.domain.valueObjects.*;
import ru.sivak.infrastructure.repositories.InMemoryComponentRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryComponentRepositoryTest {
    private InMemoryComponentRepository repository;

    private Component buildComponent(Id id, ComponentType type, ModelName model) {
        return Component.builder().
                id(id)
                .name(ComponentName.of("part"))
                .type(type)
                .price(Money.of(BigDecimal.valueOf(500)))
                .suitableModels(Set.of(model))
                .build();
    }

    @BeforeEach
    void setUp() {
        repository = new InMemoryComponentRepository(); 
    }

    @Test
    void save_and_find_returnsEntity() {
        Id id = Id.newId();
        repository.save(buildComponent(id, ComponentType.of("Wheels"), ModelName.of("Camry")));
        assertTrue(repository.find(id).isPresent());
    }

    @Test
    void query_filtersByType() {
        repository.save(buildComponent(Id.newId(), ComponentType.of("Wheels"), ModelName.of("Camry")));
        repository.save(buildComponent(Id.newId(), ComponentType.of("Interior"), ModelName.of("Camry")));
        List<Component> result = repository.query(ComponentQuery.builder()
                .type(ComponentType.of("Wheels"))
                .build());
        assertEquals(1, result.size());
    }
}
