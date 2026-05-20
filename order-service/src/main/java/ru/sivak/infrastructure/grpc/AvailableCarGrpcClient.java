package ru.sivak.infrastructure.grpc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.contract.availablecar.AvailableCarServiceGrpc;
import ru.sivak.contract.availablecar.GetAvailableCarByIdRequest;
import ru.sivak.contract.availablecar.GetAvailableCarsRequest;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.grpc.mapper.AvailableCarGrpcMapper;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvailableCarGrpcClient {

    private final AvailableCarServiceGrpc.AvailableCarServiceBlockingStub stub;
    private final AvailableCarGrpcMapper availableCarGrpcMapper;

    @Value("${storage.grpc.timeout-ms:1000}")
    private long timeoutMs;

    public List<AvailableCarDto> findAll() {
        log.info("Calling StorageService gRPC GetAvailableCars");
        return stub.withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS)
                .getAvailableCars(GetAvailableCarsRequest.newBuilder().build())
                .getCarsList()
                .stream()
                .map(availableCarGrpcMapper::map)
                .toList();
    }

    public AvailableCarDto get(Id id) {
        log.info("Calling StorageService gRPC GetAvailableCarById carId={}", id.getId());
        return availableCarGrpcMapper.map(stub.withDeadlineAfter(timeoutMs, TimeUnit.MILLISECONDS)
                .getAvailableCarById(GetAvailableCarByIdRequest.newBuilder()
                        .setId(id.getId().toString())
                        .build()));
    }
}
