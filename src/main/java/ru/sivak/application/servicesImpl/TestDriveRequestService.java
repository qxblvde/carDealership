package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.TestDriveRequestDto;
import ru.sivak.application.mappers.TestDriveMapper;
import ru.sivak.application.query.TestDriveRequestQuery;
import ru.sivak.application.services.ITestDriveRequestService;
import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.repositories.TestDriveRequestRepository;
import ru.sivak.domain.valueObjects.Id;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TestDriveRequestService implements ITestDriveRequestService {
    @NonNull
    private final TestDriveRequestRepository repository;

    public TestDriveRequestDto create(@NonNull Id clientId, @NonNull Id carId, @NonNull LocalDate scheduledTime) {
        TestDriveRequest request = TestDriveRequest.builder()
                .id(Id.newId())
                .clientId(clientId)
                .carId(carId)
                .scheduledTime(scheduledTime)
                .build();
        repository.save(request);
        return TestDriveMapper.toDto(request);
    }
    public List<TestDriveRequestDto> query(@NonNull TestDriveRequestQuery query) {
        return repository.query(query)
                .stream()
                .map(TestDriveMapper::toDto)
                .toList();
    }

    public void delete(@NonNull Id id) {
        repository.delete(id);
    }

    public TestDriveRequestDto get(@NonNull Id id) {
        return repository.find(id)
                .map(TestDriveMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));
    }

    public TestDriveRequestDto update(@NonNull Id requestId, @NonNull Id newClientId, @NonNull Id newCarId, @NonNull LocalDate newTime) {
        TestDriveRequest request = repository.find(requestId)
                .orElseThrow(() -> new IllegalArgumentException("Request not found"));

        request.updateClient(newClientId);
        request.updateCar(newCarId);
        request.updateScheduledTime(newTime);

        repository.save(request);

        return TestDriveMapper.toDto(request);
    }
}
