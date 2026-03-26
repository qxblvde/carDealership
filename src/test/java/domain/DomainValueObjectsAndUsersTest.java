package domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import ru.sivak.domain.user.Client;
import ru.sivak.domain.user.Manager;
import ru.sivak.domain.user.StorageAdministrator;
import ru.sivak.domain.user.SystemAdministrator;
import ru.sivak.domain.valueObjects.EnginePower;
import ru.sivak.domain.valueObjects.EngineVolume;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;

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
    void engine_power_rejects_negative_value() {
        //Arrange
        Executable action = () -> EnginePower.of(-1);

        //Act
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, action);

        //Assert
        assertEquals(IllegalArgumentException.class, exception.getClass());
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
