package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.sivak.domain.user.Client;
import ru.sivak.domain.user.Manager;
import ru.sivak.domain.user.StorageAdministrator;
import ru.sivak.domain.user.SystemAdministrator;
import ru.sivak.domain.valueObjects.BodyType;
import ru.sivak.domain.valueObjects.BrandName;
import ru.sivak.domain.valueObjects.ColorValue;
import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.ComponentType;
import ru.sivak.domain.valueObjects.DriveType;
import ru.sivak.domain.valueObjects.EnginePower;
import ru.sivak.domain.valueObjects.EngineVolume;
import ru.sivak.domain.valueObjects.FuelType;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;
import ru.sivak.domain.valueObjects.TransmissionType;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DomainValueObjectsAndUsersTest {

    @Test
    void money_add_returns_sum() {
        //Arrange
        Money price = Money.of(new BigDecimal("10"));
        Money extra = Money.of(new BigDecimal("4"));

        //Act
        BigDecimal total = price.add(extra).getAmount();

        //Assert
        assertEquals(new BigDecimal("14"), total);
    }

    @Test
    void money_rejects_negative_amount() {
        //Arrange
        Executable action = () -> Money.of(new BigDecimal("-1"));

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);

        //Assert
        assertEquals(IllegalArgumentException.class, exception.getClass());
    }

    @Test
    void engine_power_returns_value() {
        //Arrange
        EnginePower power = EnginePower.of(220);

        //Act
        int actual = power.getPower();

        //Assert
        assertEquals(220, actual);
    }

    @Test
    void engine_power_rejects_negative_value() {
        //Arrange
        Executable action = () -> EnginePower.of(-1);

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);

        //Assert
        assertEquals(IllegalArgumentException.class, exception.getClass());
    }

    @Test
    void engine_volume_returns_value() {
        //Arrange
        EngineVolume volume = EngineVolume.of(2000);

        //Act
        int actual = volume.getVolume();

        //Assert
        assertEquals(2000, actual);
    }

    @Test
    void engine_volume_rejects_negative_value() {
        //Arrange
        Executable action = () -> EngineVolume.of(-1);

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);

        //Assert
        assertEquals(IllegalArgumentException.class, exception.getClass());
    }

    @Test
    void body_brand_and_color_wrappers_return_values() {
        //Arrange
        BodyType bodyType = BodyType.of("Sedan");
        BrandName brandName = BrandName.of("Toyota");
        ColorValue colorValue = ColorValue.of("Black");

        //Act
        String body = bodyType.getType();
        String brand = brandName.getName();
        String color = colorValue.getColor();

        //Assert
        assertEquals("Sedan", body);
        assertEquals("Toyota", brand);
        assertEquals("Black", color);
    }

    @Test
    void component_and_drive_wrappers_return_values() {
        //Arrange
        ComponentName componentName = ComponentName.of("Premium");
        ComponentType componentType = ComponentType.of("Transmission");
        DriveType driveType = DriveType.of("example1");

        //Act
        String component = componentName.getName();
        String type = componentType.getName();
        String drive = driveType.getType();

        //Assert
        assertEquals("Premium", component);
        assertEquals("Transmission", type);
        assertEquals("example1", drive);
    }

    @Test
    void fuel_model_and_transmission_wrappers_return_values() {
        //Arrange
        FuelType fuelType = FuelType.of("Petrol");
        ModelName modelName = ModelName.of("Camry");
        TransmissionType transmissionType = TransmissionType.of("example1");

        //Act
        String fuel = fuelType.getType();
        String model = modelName.getName();
        String transmission = transmissionType.getType();

        //Assert
        assertEquals("Petrol", fuel);
        assertEquals("Camry", model);
        assertEquals("example1", transmission);
    }

    @Test
    void ids_are_unique() {
        //Arrange
        Id first = Id.newId();
        Id second = Id.newId();

        //Act
        boolean same = first.equals(second);

        //Assert
        assertNotEquals(true, same);
    }

    @Test
    void client_factory_keeps_id() {
        //Arrange
        Id id = Id.newId();

        //Act
        Id actual = Client.of(id).getId();

        //Assert
        assertEquals(id, actual);
    }

    @Test
    void manager_factory_keeps_id() {
        //Arrange
        Id id = Id.newId();

        //Act
        Id actual = Manager.of(id).getId();

        //Assert
        assertEquals(id, actual);
    }

    @Test
    void storage_admin_factory_keeps_id() {
        //Arrange
        Id id = Id.newId();

        //Act
        Id actual = StorageAdministrator.of(id).getId();

        //Assert
        assertEquals(id, actual);
    }

    @Test
    void system_admin_factory_keeps_id() {
        //Arrange
        Id id = Id.newId();

        //Act
        Id actual = SystemAdministrator.of(id).getId();

        //Assert
        assertEquals(id, actual);
    }
}
