package integration.rest;

import io.grpc.Status;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.application.services.IAvailableCarService;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.integration.rest.controller.AvailableCarController;
import ru.sivak.integration.rest.controller.RestExceptionHandler;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class AvailableCarControllerIntegrationTest {

    @Test
    void findAllReturnsCarsFromGrpcBackedService() throws Exception {
        AvailableCarDto car = availableCar();
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.findAll()).thenReturn(List.of(car));
        MockMvc mockMvc = mockMvc(availableCarService);

        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(car.id().toString()))
                .andExpect(jsonPath("$[0].brandName").value(car.brandName()))
                .andExpect(jsonPath("$[0].modelName").value(car.modelName()))
                .andExpect(jsonPath("$[0].availableQuantity").value(car.availableQuantity()));
    }

    @Test
    void getReturnsCarFromGrpcBackedService() throws Exception {
        AvailableCarDto car = availableCar();
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.get(any(Id.class))).thenReturn(car);
        MockMvc mockMvc = mockMvc(availableCarService);

        mockMvc.perform(get("/api/v1/cars/{id}", car.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(car.id().toString()))
                .andExpect(jsonPath("$.brandName").value(car.brandName()))
                .andExpect(jsonPath("$.modelName").value(car.modelName()))
                .andExpect(jsonPath("$.availableQuantity").value(car.availableQuantity()));
    }

    @Test
    void findAllReturnsServiceUnavailableWhenStorageServiceIsDown() throws Exception {
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.findAll()).thenThrow(Status.UNAVAILABLE.asRuntimeException());
        MockMvc mockMvc = mockMvc(availableCarService);

        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value(503))
                .andExpect(jsonPath("$.message").value("StorageService is unavailable"));
    }

    @Test
    void findAllReturnsServiceUnavailableWhenStorageServiceTimesOut() throws Exception {
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.findAll()).thenThrow(Status.DEADLINE_EXCEEDED
                .withDescription("StorageService request timed out")
                .asRuntimeException());
        MockMvc mockMvc = mockMvc(availableCarService);

        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.status").value(503))
                .andExpect(jsonPath("$.message").value("StorageService request timed out"));
    }

    @Test
    void getReturnsNotFoundWhenStorageDoesNotHaveRequestedCar() throws Exception {
        UUID carId = UUID.randomUUID();
        IAvailableCarService availableCarService = mock(IAvailableCarService.class);
        when(availableCarService.get(any(Id.class))).thenThrow(Status.NOT_FOUND
                .withDescription("Available car not found: " + carId)
                .asRuntimeException());
        MockMvc mockMvc = mockMvc(availableCarService);

        mockMvc.perform(get("/api/v1/cars/{id}", carId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Available car not found: " + carId));
    }

    private MockMvc mockMvc(IAvailableCarService availableCarService) {
        return standaloneSetup(new AvailableCarController(availableCarService))
                .setControllerAdvice(new RestExceptionHandler())
                .build();
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
}
