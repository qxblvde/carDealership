package domain;

import org.junit.jupiter.api.Test;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.entities.CarConfigurator;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.exceptions.DomainValidationException;
import ru.sivak.domain.exceptions.IncompatibleComponentException;
import ru.sivak.domain.valueObjects.BodyType;
import ru.sivak.domain.valueObjects.BrandName;
import ru.sivak.domain.valueObjects.ColorValue;
import ru.sivak.domain.valueObjects.ComponentName;
import ru.sivak.domain.valueObjects.DriveType;
import ru.sivak.domain.valueObjects.EnginePower;
import ru.sivak.domain.valueObjects.EngineVolume;
import ru.sivak.domain.valueObjects.FuelType;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.ModelName;
import ru.sivak.domain.valueObjects.Money;
import ru.sivak.domain.valueObjects.TransmissionType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DomainConfiguratorAndRequestTest {

    @Test
    void configurator_accepts_wheel() {
        //Arrange
        CarConfigurator configurator = new CarConfigurator(car("Camry", "15000", supportedModels()));
        Wheel wheel = wheel("450", supportedModels());

        //Act
        Runnable action = () -> configurator.addComponent(wheel);

        //Assert
        assertDoesNotThrow(action::run);
    }

    @Test
    void configurator_accepts_transmission() {
        //Arrange
        CarConfigurator configurator = new CarConfigurator(car("Camry", "15000", supportedModels()));
        Transmission transmission = transmission("example1", "1400", supportedModels());

        //Act
        Runnable action = () -> configurator.addComponent(transmission);

        //Assert
        assertDoesNotThrow(action::run);
    }

    @Test
    void configurator_accepts_steering() {
        //Arrange
        CarConfigurator configurator = new CarConfigurator(car("Camry", "15000", supportedModels()));
        Steering steering = steering("250", supportedModels());

        //Act
        Runnable action = () -> configurator.addComponent(steering);

        //Assert
        assertDoesNotThrow(action::run);
    }

    @Test
    void configurator_validates_when_all_required_components_exist() {
        //Arrange
        CarConfigurator configurator = new CarConfigurator(car("Camry", "15000", supportedModels()));
        configurator.addComponent(wheel("450", supportedModels()));
        configurator.addComponent(transmission("example1", "1400", supportedModels()));
        configurator.addComponent(steering("250", supportedModels()));
        configurator.addComponent(interior("900", supportedModels()));

        //Act
        Runnable action = configurator::validate;

        //Assert
        assertDoesNotThrow(action::run);
    }

    @Test
    void configurator_fails_when_interior_is_missing() {
        //Arrange
        CarConfigurator configurator = new CarConfigurator(car("Camry", "15000", supportedModels()));
        configurator.addComponent(wheel("450", supportedModels()));
        configurator.addComponent(transmission("example1", "1400", supportedModels()));
        configurator.addComponent(steering("250", supportedModels()));

        //Act
        DomainValidationException exception = assertThrows(DomainValidationException.class, configurator::validate);

        //Assert
        assertEquals("Node Interior is missing", exception.getMessage());
    }

    @Test
    void configurator_rejects_incompatible_component() {
        //Arrange
        CarConfigurator configurator = new CarConfigurator(car("Camry", "15000", Set.of(ModelName.of("Camry"))));
        Wheel wheel = wheel("450", Set.of(ModelName.of("Corolla")));

        //Act
        IncompatibleComponentException exception = assertThrows(IncompatibleComponentException.class, () -> configurator.addComponent(wheel));

        //Assert
        assertEquals(IncompatibleComponentException.class, exception.getClass());
    }

    @Test
    void configurator_calculates_total_price_with_optional_components() {
        //Arrange
        CarConfigurator configurator = new CarConfigurator(car("Camry", "15000", supportedModels()));
        configurator.addComponent(wheel("450", supportedModels()));
        configurator.addComponent(transmission("example1", "1400", supportedModels()));
        configurator.addComponent(steering("250", supportedModels()));
        configurator.addComponent(interior("900", supportedModels()));

        //Act
        BigDecimal total = configurator.calculatePrice().getAmount();

        //Assert
        assertEquals(new BigDecimal("18000"), total);
    }

    @Test
    void request_updates_client_car_and_date() {
        //Arrange
        TestDriveRequest request = TestDriveRequest.builder()
                .id(Id.newId())
                .clientId(Id.newId())
                .carId(Id.newId())
                .scheduledTime(LocalDate.of(2026, 3, 12))
                .build();
        Id newClientId = Id.newId();
        Id newCarId = Id.newId();
        LocalDate newDate = LocalDate.of(2026, 3, 15);

        //Act
        request.updateClient(newClientId);
        request.updateCar(newCarId);
        request.updateScheduledTime(newDate);

        //Assert
        assertEquals(newClientId, request.getClientId());
        assertEquals(newCarId, request.getCarId());
        assertEquals(newDate, request.getScheduledTime());
    }

    private static Set<ModelName> supportedModels() {
        return Set.of(ModelName.of("Camry"), ModelName.of("Corolla"));
    }

    private static Money money(String amount) {
        return Money.of(new BigDecimal(amount));
    }

    private static Body body(String type, String price, Set<ModelName> suitableModels) {
        return new Body(Id.newId(), money(price), ComponentName.of("Body"), suitableModels, BodyType.of(type));
    }

    private static Brand brand(String name, String price, Set<ModelName> suitableModels) {
        return new Brand(Id.newId(), money(price), ComponentName.of("Brand"), suitableModels, BrandName.of(name));
    }

    private static Color color(String value, String price, Set<ModelName> suitableModels) {
        return new Color(Id.newId(), money(price), ComponentName.of("Color"), suitableModels, ColorValue.of(value));
    }

    private static Drive drive(String value, String price, Set<ModelName> suitableModels) {
        return new Drive(Id.newId(), money(price), ComponentName.of("Drive"), suitableModels, DriveType.of(value));
    }

    private static Engine engine(int power, int volume, String price, Set<ModelName> suitableModels) {
        return new Engine(Id.newId(), money(price), ComponentName.of("Engine"), suitableModels, EnginePower.of(power), EngineVolume.of(volume));
    }

    private static Fuel fuel(String value, String price, Set<ModelName> suitableModels) {
        return new Fuel(Id.newId(), money(price), ComponentName.of("Fuel"), suitableModels, FuelType.of(value));
    }

    private static Interior interior(String price, Set<ModelName> suitableModels) {
        return new Interior(Id.newId(), money(price), ComponentName.of("Interior"), suitableModels);
    }

    private static Model model(String value, String price, Set<ModelName> suitableModels) {
        return new Model(Id.newId(), money(price), ComponentName.of("Model"), suitableModels, ModelName.of(value));
    }

    private static Transmission transmission(String value, String price, Set<ModelName> suitableModels) {
        return new Transmission(Id.newId(), money(price), ComponentName.of("Transmission"), suitableModels, TransmissionType.of(value));
    }

    private static Wheel wheel(String price, Set<ModelName> suitableModels) {
        return new Wheel(Id.newId(), money(price), ComponentName.of("Wheel"), suitableModels);
    }

    private static Steering steering(String price, Set<ModelName> suitableModels) {
        return new Steering(Id.newId(), money(price), ComponentName.of("Steering"), suitableModels);
    }

    private static Car car(String modelName, String price, Set<ModelName> suitableModels) {
        return Car.builder()
                .id(Id.newId())
                .bodyType(body("Sedan", "1000", suitableModels))
                .brandName(brand("Toyota", "1500", suitableModels))
                .color(color("Black", "300", suitableModels))
                .driveType(drive("example1", "700", suitableModels))
                .engine(engine(220, 2000, "2400", suitableModels))
                .fuel(fuel("Petrol", "200", suitableModels))
                .model(model(modelName, "1200", suitableModels))
                .price(money(price))
                .transmission(transmission("example1", "1400", suitableModels))
                .steering(steering("250", suitableModels))
                .wheel(wheel("450", suitableModels))
                .interior(interior("900", suitableModels))
                .build();
    }
}
