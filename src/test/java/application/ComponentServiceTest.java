package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sivak.application.dto.ComponentDto;
import ru.sivak.application.query.ComponentQuery;
import ru.sivak.application.servicesImpl.ComponentService;
import ru.sivak.domain.entities.Component;
import ru.sivak.domain.repositories.ComponentRepository;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ComponentServiceTest {
    @Mock
    private ComponentRepository repository;
    private ComponentService service;

    private Component buildComponent(Id id) {
        return Component.builder()
                .id(id).name(ComponentName.of("Wheel"))
                .type(ComponentType.of("Wheels"))
                .price(Money.of(BigDecimal.valueOf(500)))
                .suitableModels(Set.of(ModelName.of("Camry"))).build();
    }

    @BeforeEach
    void setUp() {
        service = new ComponentService(repository);
    }

    @Test
    void save_returnsDto() {
        Id id = Id.newId();
        Component c = buildComponent(id);
        ComponentDto dto = service.save(c);
        verify(repository).save(c);
        assertEquals(id, dto.id());
    }

    @Test
    void delete_callsRepository() {
        Id id = Id.newId();
        service.delete(id);
        verify(repository).delete(id);
    }

    @Test
    void query_returnsDto() {
        Id id = Id.newId();
        ComponentQuery query = ComponentQuery.builder()
                .type(ComponentType.of("Wheels"))
                .build();
        when(repository.query(query)).thenReturn(List.of(buildComponent(id)));
        assertEquals(1, service.query(query).size());
    }

    @Test
    void get_returnsDto() {
        Id id = Id.newId();
        when(repository.find(id)).thenReturn(Optional.of(buildComponent(id)));
        assertEquals(id, service.get(id).id());
    }

    @Test
    void get_throws_IfNotFound() {
        Id id = Id.newId();
        when(repository.find(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.get(id));
    }
}
