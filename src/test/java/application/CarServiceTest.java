package application;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sivak.application.dto.CarDto;
import ru.sivak.application.query.CarQuery;
import ru.sivak.application.servicesImpl.CarService;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceTest {
    @Mock
    private CarRepository repository;
    private CarService service;

    private Car buildCar(Id id) {
        return Car.builder()
                .id(id).bodyType(BodyType.of("Sedan"))
                .brandName(BrandName.of("Toyota"))
                .color(Color.of("White"))
                .driveType(DriveType.of("abs"))
                .enginePower(EnginePower.of(200))
                .engineVolume(EngineVolume.of(2000))
                .fuelType(FuelType.of("Petrol"))
                .modelName(ModelName.of("Camry"))
                .price(Money.of(BigDecimal.valueOf(20000)))
                .transmissionType(TransmissionType.of("Auto"))
                .build();
    }

    @BeforeEach
    void setUp() {
        service = new CarService(repository);
    }

    @Test
    void save_returnsDto() {
        Id id = Id.newId();
        Car car = buildCar(id);
        CarDto dto = service.save(car);
        verify(repository).save(car);
        assertEquals(id, dto.id());
    }

    @Test
    void delete_callsRepository() {
        Id id = Id.newId();
        service.delete(id);
        verify(repository).delete(id);
    }

    @Test
    void findAll_returnsDto() {
        Id id = Id.newId();
        when(repository.findAll()).thenReturn(List.of(buildCar(id)));
        assertEquals(1, service.findAll().size());
    }

    @Test
    void query_returnsDto() {
        Id id = Id.newId();
        CarQuery query = CarQuery.builder()
                .brandName(BrandName.of("Toyota"))
                .build();
        when(repository.query(query)).thenReturn(List.of(buildCar(id)));
        assertEquals(1, service.query(query).size());
    }

    @Test
    void get_returnsDto() {
        Id id = Id.newId();
        when(repository.find(id)).thenReturn(Optional.of(buildCar(id)));
        assertEquals(id, service.get(id).id());
    }

    @Test
    void get_throws_IfNotFound() {
        Id id = Id.newId();
        when(repository.find(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.get(id));
    }
}
