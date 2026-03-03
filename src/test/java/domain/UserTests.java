package domain;

import org.junit.jupiter.api.Test;
import ru.sivak.domain.user.Client;
import ru.sivak.domain.user.Manager;
import ru.sivak.domain.user.StorageAdministrator;
import ru.sivak.domain.user.SystemAdministrator;
import ru.sivak.domain.valueObjects.Id;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {
    @Test
    void of_createsCorrectly() {
        Id id = Id.newId();

        Client client = Client.of(id);
        Manager manager = Manager.of(id);
        StorageAdministrator storageAdmin = StorageAdministrator.of(id);
        SystemAdministrator systemAdmin = SystemAdministrator.of(id);

        assertEquals(id, client.getId());
        assertEquals(id, manager.getId());
        assertEquals(id, storageAdmin.getId());
        assertEquals(id, systemAdmin.getId());
    }
}
