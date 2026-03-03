package infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sivak.application.query.CarQuery;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.valueObjects.*;
import ru.sivak.infrastructure.repositories.InMemoryCarRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryCarRepositoryTest {
    private InMemoryCarRepository repository;

    private Car buildCar(Id id, BrandName brand, Money price) {
        return Car.builder()
                .id(id).bodyType(BodyType.of("Sedan"))
                .brandName(brand)
                .color(Color.of("White"))
                .driveType(DriveType.of("abs"))
                .enginePower(EnginePower.of(200))
                .engineVolume(EngineVolume.of(2000))
                .fuelType(FuelType.of("Petrol"))
                .modelName(ModelName.of("Camry"))
                .price(price)
                .transmissionType(TransmissionType.of("Auto"))
                .build();
    }

    @BeforeEach
    void setUp() {
        repository = new InMemoryCarRepository();
    }

    @Test
    void save_and_find_returnsEntity() {
        Id id = Id.newId();
        repository.save(buildCar(id, BrandName.of("Toyota"), Money.of(BigDecimal.valueOf(20000))));
        assertTrue(repository.find(id).isPresent());
    }

    @Test
    void query_filtersByBrandName() {
        repository.save(buildCar(Id.newId(), BrandName.of("Toyota"), Money.of(BigDecimal.valueOf(20000))));
        repository.save(buildCar(Id.newId(), BrandName.of("Honda"), Money.of(BigDecimal.valueOf(18000))));
        List<Car> result = repository.query(CarQuery.builder()
                .brandName(BrandName.of("Toyota"))
                .build());
        assertEquals(1, result.size());
    }
}
