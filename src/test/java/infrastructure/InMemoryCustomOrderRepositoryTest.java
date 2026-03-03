package infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.order.custom.CanceledState;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.valueObjects.*;
import ru.sivak.infrastructure.repositories.InMemoryCustomOrderRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryCustomOrderRepositoryTest {
    private InMemoryCustomOrderRepository repository;

    private Car buildCar() {
        return Car.builder()
                .id(Id.newId()).bodyType(BodyType.of("Sedan"))
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
        repository = new InMemoryCustomOrderRepository(); 
    }

    @Test
    void save_and_find_returnsEntity() {
        Id id = Id.newId();
        repository.save(new CustomOrder(id, Id.newId(), Id.newId(), buildCar()));
        assertTrue(repository.find(id).isPresent());
    }

    @Test
    void query_filtersByState() {
        CustomOrder active = new CustomOrder(Id.newId(), Id.newId(), Id.newId(), buildCar());
        CustomOrder canceled = new CustomOrder(Id.newId(), Id.newId(), Id.newId(), buildCar());
        canceled.cancel();
        repository.save(active);
        repository.save(canceled);
        List<CustomOrder> result = repository.query(CustomOrderQuery.builder()
                .stateType(CanceledState.class)
                .build());
        assertEquals(1, result.size());
    }
}
