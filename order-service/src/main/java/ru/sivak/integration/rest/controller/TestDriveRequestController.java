package ru.sivak.integration.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sivak.application.dto.TestDriveRequestDto;
import ru.sivak.application.services.ITestDriveRequestService;
import ru.sivak.integration.rest.dto.CreateTestDriveRequest;
import ru.sivak.integration.rest.dto.UpdateTestDriveRequest;
import ru.sivak.integration.rest.mapper.TestDriveRequestRestMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/test-drive-requests")
@RequiredArgsConstructor
public class TestDriveRequestController {
    private final ITestDriveRequestService testDriveRequestService;
    private final TestDriveRequestRestMapper testDriveRequestRestMapper;

    @PostMapping
    public TestDriveRequestDto create(@RequestBody CreateTestDriveRequest request) {
        TestDriveRequestRestMapper.CreateCommand command = testDriveRequestRestMapper.toCreateCommand(request);
        return testDriveRequestService.create(command.carId(), command.scheduledTime());
    }

    @GetMapping
    public List<TestDriveRequestDto> query(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) UUID carId,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate
    ) {
        return testDriveRequestService.query(testDriveRequestRestMapper.toQuery(clientId, carId, fromDate, toDate));
    }

    @GetMapping("/{id}")
    public TestDriveRequestDto getById(@PathVariable UUID id) {
        return testDriveRequestService.get(testDriveRequestRestMapper.toId(id));
    }

    @PutMapping("/{id}")
    public TestDriveRequestDto update(@PathVariable UUID id, @RequestBody UpdateTestDriveRequest request) {
        TestDriveRequestRestMapper.UpdateCommand command = testDriveRequestRestMapper.toUpdateCommand(id, request);
        return testDriveRequestService.update(command.requestId(), command.carId(), command.scheduledTime());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        testDriveRequestService.delete(testDriveRequestRestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }
}
