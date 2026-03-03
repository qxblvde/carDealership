package domain;

import org.junit.jupiter.api.Test;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.entities.CarConfigurator;
import ru.sivak.domain.entities.Component;
import ru.sivak.domain.exceptions.DomainValidationException;
import ru.sivak.domain.exceptions.IncompatibleComponentException;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class CarConfiguratorTest {
    private Car buildCar(ModelName modelName) {
        return Car.builder()
                .id(Id.newId()).bodyType(BodyType.of("Sedan"))
                .brandName(BrandName.of("Toyota"))
                .color(Color.of("White"))
                .driveType(DriveType.of("abc"))
                .enginePower(EnginePower.of(200))
                .engineVolume(EngineVolume.of(2000))
                .fuelType(FuelType.of("Petrol"))
                .modelName(modelName)
                .price(Money.of(BigDecimal.valueOf(20000)))
                .transmissionType(TransmissionType.of("Auto"))
                .build();
    }

    private Component buildComponent(ComponentType type, ModelName... models) {
        return Component.builder().id(Id.newId()).name(ComponentName.of("part"))
                .type(type).price(Money.of(BigDecimal.valueOf(500))).suitableModels(Set.of(models)).build();
    }

    @Test
    void addComponent_throws_ifIncompatible() {
        ModelName model = ModelName.of("camry");
        CarConfigurator carConfigurator = new CarConfigurator(buildCar(model));
        ComponentType type = ComponentType.of("wheels");
        assertThrows(IncompatibleComponentException.class,
                () -> carConfigurator.addComponent(type, buildComponent(type, ModelName.of("other"))));
    }

    @Test
    void validate_throws_ifRequiredComponentsMissing() {
        assertThrows(DomainValidationException.class,
                () -> new CarConfigurator(buildCar(ModelName.of("camry"))).validate());
    }
}
