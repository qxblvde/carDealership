package integration.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.application.services.IAvailableCarService;
import ru.sivak.contract.availablecar.AvailableCarResponse;
import ru.sivak.contract.availablecar.GetAvailableCarByIdRequest;
import ru.sivak.contract.availablecar.GetAvailableCarsRequest;
import ru.sivak.contract.availablecar.GetAvailableCarsResponse;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.integration.grpc.AvailableCarGrpcService;
import ru.sivak.integration.grpc.mapper.AvailableCarGrpcMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AvailableCarGrpcServiceIntegrationTest {

    @Mock
    private StreamObserver<GetAvailableCarsResponse> carsObserver;

    @Mock
    private StreamObserver<AvailableCarResponse> carObserver;

    @Test
    void getAvailableCarsReturnsMappedAvailableCars() {
        AvailableCarDto car = availableCar();
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.findAllAvailable()).thenReturn(List.of(car));
        AvailableCarGrpcService grpcService = grpcService(availableCarService);

        grpcService.getAvailableCars(GetAvailableCarsRequest.newBuilder().build(), carsObserver);

        ArgumentCaptor<GetAvailableCarsResponse> responseCaptor = ArgumentCaptor.forClass(GetAvailableCarsResponse.class);
        verify(carsObserver).onNext(responseCaptor.capture());
        verify(carsObserver).onCompleted();
        assertThat(responseCaptor.getValue().getCarsList()).hasSize(1);
        assertCarResponse(responseCaptor.getValue().getCars(0), car);
    }

    @Test
    void getAvailableCarsReturnsEmptyResultWhenNoCarsAvailable() {
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.findAllAvailable()).thenReturn(List.of());
        AvailableCarGrpcService grpcService = grpcService(availableCarService);

        grpcService.getAvailableCars(GetAvailableCarsRequest.newBuilder().build(), carsObserver);

        ArgumentCaptor<GetAvailableCarsResponse> responseCaptor = ArgumentCaptor.forClass(GetAvailableCarsResponse.class);
        verify(carsObserver).onNext(responseCaptor.capture());
        verify(carsObserver).onCompleted();
        assertThat(responseCaptor.getValue().getCarsList()).isEmpty();
    }

    @Test
    void getAvailableCarByIdReturnsRequestedAvailableCar() {
        AvailableCarDto car = availableCar();
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.findAvailable(Id.of(car.id()))).thenReturn(Optional.of(car));
        AvailableCarGrpcService grpcService = grpcService(availableCarService);

        grpcService.getAvailableCarById(GetAvailableCarByIdRequest.newBuilder()
                .setId(car.id().toString())
                .build(), carObserver);

        ArgumentCaptor<AvailableCarResponse> responseCaptor = ArgumentCaptor.forClass(AvailableCarResponse.class);
        verify(carObserver).onNext(responseCaptor.capture());
        verify(carObserver).onCompleted();
        assertCarResponse(responseCaptor.getValue(), car);
    }

    @Test
    void getAvailableCarByIdReturnsNotFoundWhenCarIsUnavailable() {
        UUID missingCarId = UUID.randomUUID();
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.findAvailable(Id.of(missingCarId))).thenReturn(Optional.empty());
        AvailableCarGrpcService grpcService = grpcService(availableCarService);

        grpcService.getAvailableCarById(GetAvailableCarByIdRequest.newBuilder()
                .setId(missingCarId.toString())
                .build(), carObserver);

        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(carObserver).onError(errorCaptor.capture());
        verify(carObserver, never()).onCompleted();
        StatusRuntimeException exception = (StatusRuntimeException) errorCaptor.getValue();
        assertThat(exception.getStatus().getCode()).isEqualTo(Status.Code.NOT_FOUND);
        assertThat(exception.getStatus().getDescription()).contains(missingCarId.toString());
    }

    @Test
    void getAvailableCarByIdRejectsInvalidCarId() {
        AvailableCarGrpcService grpcService = grpcService(mock(IAvailableCarService.class));

        grpcService.getAvailableCarById(GetAvailableCarByIdRequest.newBuilder()
                .setId("not-a-uuid")
                .build(), carObserver);

        ArgumentCaptor<Throwable> errorCaptor = ArgumentCaptor.forClass(Throwable.class);
        verify(carObserver).onError(errorCaptor.capture());
        verify(carObserver, never()).onCompleted();
        StatusRuntimeException exception = (StatusRuntimeException) errorCaptor.getValue();
        assertThat(exception.getStatus().getCode()).isEqualTo(Status.Code.INVALID_ARGUMENT);
        assertThat(exception.getStatus().getDescription()).isEqualTo("Invalid car id");
    }

    private AvailableCarGrpcService grpcService(IAvailableCarService availableCarService) {
        return new AvailableCarGrpcService(availableCarService, new AvailableCarGrpcMapper() {
        });
    }

    private AvailableCarDto availableCar() {
        return new AvailableCarDto(
                UUID.randomUUID(),
                "SEDAN",
                "Toyota",
                "Black",
                "AWD",
                249,
                2500,
                "GASOLINE",
                "Camry",
                new BigDecimal("3450000.00"),
                "AUTOMATIC",
                3
        );
    }

    private void assertCarResponse(AvailableCarResponse response, AvailableCarDto expected) {
        assertThat(response.getId()).isEqualTo(expected.id().toString());
        assertThat(response.getBodyType()).isEqualTo(expected.bodyType());
        assertThat(response.getBrandName()).isEqualTo(expected.brandName());
        assertThat(response.getColor()).isEqualTo(expected.color());
        assertThat(response.getDriveType()).isEqualTo(expected.driveType());
        assertThat(response.getEnginePower()).isEqualTo(expected.enginePower());
        assertThat(response.getEngineVolume()).isEqualTo(expected.engineVolume());
        assertThat(response.getFuelType()).isEqualTo(expected.fuelType());
        assertThat(response.getModelName()).isEqualTo(expected.modelName());
        assertThat(response.getPrice()).isEqualTo(expected.price().toPlainString());
        assertThat(response.getTransmissionType()).isEqualTo(expected.transmissionType());
        assertThat(response.getAvailableQuantity()).isEqualTo(expected.availableQuantity());
    }

}
