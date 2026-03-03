package infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.repositories.InMemoryTestDriveRequestRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryTestDriveRequestRepositoryTest {
    private InMemoryTestDriveRequestRepository repository;

    private TestDriveRequest buildRequest(Id id, Id clientId, LocalDate date) {
        return TestDriveRequest.builder()
                .id(id)
                .clientId(clientId)
                .carId(Id.newId())
                .scheduledTime(date)
                .build();
    }

    @BeforeEach
    void setUp() {
        repository = new InMemoryTestDriveRequestRepository();
    }

    @Test
    void save_and_find_returnsEntity() {
        Id id = Id.newId();
        repository.save(buildRequest(id, Id.newId(), LocalDate.of(2026, 3, 1)));
        assertTrue(repository.find(id).isPresent());
    }

    @Test
    void query_filtersByClientId() {
        Id clientId = Id.newId();
        repository.save(buildRequest(Id.newId(), clientId, LocalDate.of(2026, 3, 1)));
        repository.save(buildRequest(Id.newId(), Id.newId(), LocalDate.of(2026, 3, 2)));
        List<TestDriveRequest> result = repository.query(TestDriveRequestQuery.builder()
                .clientId(clientId)
                .build());
        assertEquals(1, result.size());
    }

    @Test
    void query_filtersByDateRange() {
        Id clientId = Id.newId();
        repository.save(buildRequest(Id.newId(), clientId, LocalDate.of(2026, 3, 1)));
        repository.save(buildRequest(Id.newId(), clientId, LocalDate.of(2026, 4, 1)));
        List<TestDriveRequest> result = repository.query(TestDriveRequestQuery.builder()
                .fromDate(LocalDate.of(2026, 3, 1))
                .toDate(LocalDate.of(2026, 3, 31))
                .build());
        assertEquals(1, result.size());
    }
}
