package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.TestDriveRequestDto;
import ru.sivak.application.mappers.TestDriveMapper;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.application.services.ITestDriveRequestService;
import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.repositories.TestDriveRequestRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.security.AuthenticatedUserService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TestDriveRequestService implements ITestDriveRequestService {
    @NonNull
    private final TestDriveRequestRepository repository;
    @NonNull
    private final TestDriveMapper testDriveMapper;
    @NonNull
    private final AuthenticatedUserService authenticatedUserService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public TestDriveRequestDto create(@NonNull Id carId, @NonNull LocalDate scheduledTime) {
        Id clientId = authenticatedUserService.getCurrentUserId();
        TestDriveRequest request = TestDriveRequest.builder()
                .id(Id.newId())
                .clientId(clientId)
                .carId(carId)
                .scheduledTime(scheduledTime)
                .build();

        repository.create(request);
        return testDriveMapper.map(request);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public List<TestDriveRequestDto> query(@NonNull TestDriveRequestQuery query) {
        TestDriveRequestQuery dublicate = query;
        if (!authenticatedUserService.hasRole("MANAGER") && !authenticatedUserService.hasRole("ADMIN")) {
            dublicate = query.toBuilder()
                    .clientId(authenticatedUserService.getCurrentUserId())
                    .build();
        }
        return repository.query(dublicate)
                .stream()
                .map(testDriveMapper::map)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderAccessChecker.isCurrentTestDriveOwner(#id)")
    public void delete(@NonNull Id id) {
        repository.delete(id);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderAccessChecker.isCurrentTestDriveOwner(#id)")
    public TestDriveRequestDto get(@NonNull Id id) {
        return repository.find(id)
                .map(testDriveMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or @orderAccessChecker.isCurrentTestDriveOwner(#requestId)")
    public TestDriveRequestDto update(@NonNull Id requestId, @NonNull Id newCarId, @NonNull LocalDate newTime) {
        TestDriveRequest request = repository.find(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.updateCar(newCarId);
        request.updateScheduledTime(newTime);

        repository.update(request);

        return testDriveMapper.map(request);
    }
}
