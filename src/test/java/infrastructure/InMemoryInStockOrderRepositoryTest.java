package infrastructure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.order.inStock.CanceledState;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.valueObjects.*;
import ru.sivak.infrastructure.repositories.InMemoryInStockOrderRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InMemoryInStockOrderRepositoryTest {
    private InMemoryInStockOrderRepository repository;

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
        repository = new InMemoryInStockOrderRepository();
    }

    @Test
    void save_and_find_returnsEntity() {
        Id id = Id.newId();
        repository.save(new InStockOrder(id, Id.newId(), Id.newId(), buildCar()));
        assertTrue(repository.find(id).isPresent());
    }

    @Test
    void query_filtersByClientId() {
        Id clientId = Id.newId();
        repository.save(new InStockOrder(Id.newId(), Id.newId(), clientId, buildCar()));
        repository.save(new InStockOrder(Id.newId(), Id.newId(), Id.newId(), buildCar()));
        List<InStockOrder> result = repository.query(InStockOrderQuery.builder()
                .clientId(clientId)
                .build());
        assertEquals(1, result.size());
    }

    @Test
    void query_filtersByState() {
        InStockOrder active = new InStockOrder(Id.newId(), Id.newId(), Id.newId(), buildCar());
        InStockOrder canceled = new InStockOrder(Id.newId(), Id.newId(), Id.newId(), buildCar());
        canceled.cancel();
        repository.save(active);
        repository.save(canceled);
        List<InStockOrder> result = repository.query(InStockOrderQuery.builder()
                .stateType(CanceledState.class)
                .build());
        assertEquals(1, result.size());
    }
}
