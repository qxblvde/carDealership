package infrastructure.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.contract.availablecar.AvailableCarResponse;
import ru.sivak.contract.availablecar.AvailableCarServiceGrpc;
import ru.sivak.contract.availablecar.GetAvailableCarByIdRequest;
import ru.sivak.contract.availablecar.GetAvailableCarsRequest;
import ru.sivak.contract.availablecar.GetAvailableCarsResponse;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.grpc.AvailableCarGrpcClient;
import ru.sivak.infrastructure.grpc.mapper.AvailableCarGrpcMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowableOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AvailableCarGrpcClientIntegrationTest {

    private AvailableCarServiceGrpc.AvailableCarServiceBlockingStub stub;
    private AvailableCarGrpcClient client;

    @BeforeEach
    void setUp() {
        stub = mock(AvailableCarServiceGrpc.AvailableCarServiceBlockingStub.class);
        client = new AvailableCarGrpcClient(stub, new AvailableCarGrpcMapper() {
        });
        ReflectionTestUtils.setField(client, "timeoutMs", 1_000L);
        when(stub.withDeadlineAfter(1_000L, TimeUnit.MILLISECONDS)).thenReturn(stub);
    }

    @Test
    void findAllMapsStorageGrpcResponse() {
        AvailableCarResponse car = availableCarResponse(UUID.randomUUID());
        when(stub.getAvailableCars(any(GetAvailableCarsRequest.class))).thenReturn(
                GetAvailableCarsResponse.newBuilder()
                        .addAllCars(List.of(car))
                        .build()
        );

        List<AvailableCarDto> result = client.findAll();

        assertThat(result).hasSize(1);
        assertCarDto(result.get(0), car);
        verify(stub).withDeadlineAfter(1_000L, TimeUnit.MILLISECONDS);
        verify(stub).getAvailableCars(any(GetAvailableCarsRequest.class));
    }

    @Test
    void findAllReturnsEmptyListWhenStorageHasNoAvailableCars() {
        when(stub.getAvailableCars(any(GetAvailableCarsRequest.class))).thenReturn(
                GetAvailableCarsResponse.newBuilder().build()
        );

        List<AvailableCarDto> result = client.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void getMapsStorageGrpcResponseById() {
        UUID carId = UUID.randomUUID();
        AvailableCarResponse car = availableCarResponse(carId);
        when(stub.getAvailableCarById(any(GetAvailableCarByIdRequest.class))).thenReturn(car);

        AvailableCarDto result = client.get(Id.of(carId));

        assertCarDto(result, car);
        verify(stub).withDeadlineAfter(1_000L, TimeUnit.MILLISECONDS);
        verify(stub).getAvailableCarById(GetAvailableCarByIdRequest.newBuilder()
                .setId(carId.toString())
                .build());
    }

    @Test
    void findAllFailsWithDeadlineExceededWhenStorageDoesNotRespondInTime() {
        ReflectionTestUtils.setField(client, "timeoutMs", 20L);
        when(stub.withDeadlineAfter(20L, TimeUnit.MILLISECONDS)).thenReturn(stub);
        when(stub.getAvailableCars(any(GetAvailableCarsRequest.class)))
                .thenThrow(Status.DEADLINE_EXCEEDED.asRuntimeException());

        StatusRuntimeException exception = catchThrowableOfType(
                client::findAll,
                StatusRuntimeException.class
        );

        assertThat(exception.getStatus().getCode()).isEqualTo(Status.Code.DEADLINE_EXCEEDED);
        verify(stub).withDeadlineAfter(20L, TimeUnit.MILLISECONDS);
    }

    @Test
    void findAllFailsWithUnavailableWhenStorageServiceIsDown() {
        when(stub.getAvailableCars(any(GetAvailableCarsRequest.class)))
                .thenThrow(Status.UNAVAILABLE.asRuntimeException());

        StatusRuntimeException exception = catchThrowableOfType(
                client::findAll,
                StatusRuntimeException.class
        );

        assertThat(exception.getStatus().getCode()).isEqualTo(Status.Code.UNAVAILABLE);
    }

    private AvailableCarResponse availableCarResponse(UUID carId) {
        return AvailableCarResponse.newBuilder()
                .setId(carId.toString())
                .setBodyType("SUV")
                .setBrandName("BMW")
                .setColor("White")
                .setDriveType("AWD")
                .setEnginePower(340)
                .setEngineVolume(3000)
                .setFuelType("GASOLINE")
                .setModelName("X5")
                .setPrice("7200000.50")
                .setTransmissionType("AUTOMATIC")
                .setAvailableQuantity(2)
                .build();
    }

    private void assertCarDto(AvailableCarDto actual, AvailableCarResponse expected) {
        assertThat(actual.id()).isEqualTo(UUID.fromString(expected.getId()));
        assertThat(actual.bodyType()).isEqualTo(expected.getBodyType());
        assertThat(actual.brandName()).isEqualTo(expected.getBrandName());
        assertThat(actual.color()).isEqualTo(expected.getColor());
        assertThat(actual.driveType()).isEqualTo(expected.getDriveType());
        assertThat(actual.enginePower()).isEqualTo(expected.getEnginePower());
        assertThat(actual.engineVolume()).isEqualTo(expected.getEngineVolume());
        assertThat(actual.fuelType()).isEqualTo(expected.getFuelType());
        assertThat(actual.modelName()).isEqualTo(expected.getModelName());
        assertThat(actual.price()).isEqualByComparingTo(new BigDecimal(expected.getPrice()));
        assertThat(actual.transmissionType()).isEqualTo(expected.getTransmissionType());
        assertThat(actual.availableQuantity()).isEqualTo(expected.getAvailableQuantity());
    }
}
