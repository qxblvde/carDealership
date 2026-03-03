package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sivak.application.dto.TestDriveRequestDto;
import ru.sivak.application.servicesImpl.TestDriveRequestService;
import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.repositories.TestDriveRequestRepository;
import ru.sivak.domain.valueObjects.Id;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TestDriveRequestServiceTest {
    @Mock
    private TestDriveRequestRepository repository;
    private TestDriveRequestService service;

    @BeforeEach
    void setUp() {
        service = new TestDriveRequestService(repository);
    }

    @Test
    void create_returnsDto() {
        Id clientId = Id.newId();
        Id carId = Id.newId();
        LocalDate date = LocalDate.of(2026, 3, 1);
        TestDriveRequestDto dto = service.create(clientId, carId, date);
        verify(repository).save(any(TestDriveRequest.class));
        assertEquals(clientId, dto.clientId());
    }

    @Test
    void delete_callsRepository() {
        Id id = Id.newId();
        service.delete(id);
        verify(repository).delete(id);
    }

    @Test
    void get_returnsDto_ifFound() {
        Id id = Id.newId();
        TestDriveRequest req = TestDriveRequest.builder()
                .id(id).clientId(Id.newId())
                .carId(Id.newId())
                .scheduledTime(LocalDate.of(2026, 3, 1))
                .build();
        when(repository.find(id)).thenReturn(Optional.of(req));
        assertEquals(id, service.get(id).id());
    }

    @Test
    void get_throws_ifNotFound() {
        Id id = Id.newId();
        when(repository.find(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.get(id));
    }
}
