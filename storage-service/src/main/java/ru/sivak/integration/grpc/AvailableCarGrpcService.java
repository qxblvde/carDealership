package ru.sivak.integration.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.sivak.application.services.IAvailableCarService;
import ru.sivak.contract.availablecar.AvailableCarResponse;
import ru.sivak.contract.availablecar.AvailableCarServiceGrpc;
import ru.sivak.contract.availablecar.GetAvailableCarByIdRequest;
import ru.sivak.contract.availablecar.GetAvailableCarsRequest;
import ru.sivak.contract.availablecar.GetAvailableCarsResponse;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.integration.grpc.mapper.AvailableCarGrpcMapper;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AvailableCarGrpcService extends AvailableCarServiceGrpc.AvailableCarServiceImplBase {

    private final IAvailableCarService availableCarService;
    private final AvailableCarGrpcMapper availableCarGrpcMapper;

    @Override
    public void getAvailableCars(
            GetAvailableCarsRequest request,
            StreamObserver<GetAvailableCarsResponse> responseObserver
    ) {
        log.info("gRPC GetAvailableCars request received");

        GetAvailableCarsResponse response = GetAvailableCarsResponse.newBuilder()
                .addAllCars(availableCarService.findAllAvailable()
                        .stream()
                        .map(availableCarGrpcMapper::map)
                        .toList())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAvailableCarById(
            GetAvailableCarByIdRequest request,
            StreamObserver<AvailableCarResponse> responseObserver
    ) {
        log.info("gRPC GetAvailableCarById request received carId={}", request.getId());

        UUID carId;
        try {
            carId = parseCarId(request.getId());
        } catch (StatusRuntimeException exception) {
            responseObserver.onError(exception);
            return;
        }

        Optional<AvailableCarResponse> response = availableCarService.findAvailable(Id.of(carId))
                .map(availableCarGrpcMapper::map);
        if (response.isEmpty()) {
            responseObserver.onError(notFound(request.getId()));
            return;
        }

        responseObserver.onNext(response.get());
        responseObserver.onCompleted();
    }

    private UUID parseCarId(String value) {
        try {
            return UUID.fromString(value);
        } catch (IllegalArgumentException exception) {
            throw Status.INVALID_ARGUMENT
                    .withDescription("Invalid car id")
                    .asRuntimeException();
        }
    }

    private StatusRuntimeException notFound(String carId) {
        return Status.NOT_FOUND
                .withDescription("Available car not found: " + carId)
                .asRuntimeException();
    }
}
