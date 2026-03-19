package domain;

import org.junit.jupiter.api.Test;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.entities.Wheel;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainEntitiesTest {

    @Test
    void body_exposes_body_type() {
        //Arrange
        Body body = body("Sedan", "1000", supportedModels());

        //Act
        BodyType actual = body.getBodyType();

        //Assert
        assertEquals(BodyType.of("Sedan"), actual);
    }

    @Test
    void brand_exposes_name() {
        //Arrange
        Brand brand = brand("Toyota", "1500", supportedModels());

        //Act
        BrandName actual = brand.getName();

        //Assert
        assertEquals(BrandName.of("Toyota"), actual);
    }

    @Test
    void color_exposes_color() {
        //Arrange
        Color color = color("Black", "300", supportedModels());

        //Act
        ColorValue actual = color.getColor();

        //Assert
        assertEquals(ColorValue.of("Black"), actual);
    }

    @Test
    void drive_exposes_drive_type() {
        //Arrange
        Drive drive = drive("example1", "700", supportedModels());

        //Act
        DriveType actual = drive.getDriveType();

        //Assert
        assertEquals(DriveType.of("example1"), actual);
    }

    @Test
    void engine_exposes_power() {
        //Arrange
        Engine engine = engine(220, 2000, "2400", supportedModels());

        //Act
        EnginePower actual = engine.getPower();

        //Assert
        assertEquals(EnginePower.of(220), actual);
    }

    @Test
    void engine_exposes_volume() {
        //Arrange
        Engine engine = engine(220, 2000, "2400", supportedModels());

        //Act
        EngineVolume actual = engine.getVolume();

        //Assert
        assertEquals(EngineVolume.of(2000), actual);
    }

    @Test
    void fuel_exposes_fuel_type() {
        //Arrange
        Fuel fuel = fuel("Petrol", "200", supportedModels());

        //Act
        FuelType actual = fuel.getFuelType();

        //Assert
        assertEquals(FuelType.of("Petrol"), actual);
    }

    @Test
    void model_exposes_model_name() {
        //Arrange
        Model model = model("Camry", "1200", supportedModels());

        //Act
        ModelName actual = model.getModelName();

        //Assert
        assertEquals(ModelName.of("Camry"), actual);
    }

    @Test
    void transmission_exposes_type() {
        //Arrange
        Transmission transmission = transmission("example1", "1400", supportedModels());

        //Act
        TransmissionType actual = transmission.getTransmissionType();

        //Assert
        assertEquals(TransmissionType.of("example1"), actual);
    }

    @Test
    void wheel_steering_and_interior_expose_component_types() {
        //Arrange
        Wheel wheel = wheel("450", supportedModels());
        Steering steering = steering("250", supportedModels());
        Interior interior = interior("900", supportedModels());

        //Act
        ComponentType wheelType = wheel.getComponentType();
        ComponentType steeringType = steering.getComponentType();
        ComponentType interiorType = interior.getComponentType();

        //Assert
        assertEquals(ComponentType.of("Wheels"), wheelType);
        assertEquals(ComponentType.of("Steering"), steeringType);
        assertEquals(ComponentType.of("Interior"), interiorType);
    }

    @Test
    void wheel_checks_supported_model() {
        //Arrange
        Wheel wheel = wheel("450", Set.of(ModelName.of("Camry")));

        //Act
        boolean actual = wheel.isSuitableWith(ModelName.of("Camry"));

        //Assert
        assertTrue(actual);
    }

    @Test
    void wheel_rejects_unsupported_model() {
        //Arrange
        Wheel wheel = wheel("450", Set.of(ModelName.of("Camry")));

        //Act
        boolean actual = wheel.isSuitableWith(ModelName.of("Corolla"));

        //Assert
        assertFalse(actual);
    }

    @Test
    void car_contains_required_component_types() {
        //Arrange
        Car car = car("Camry", "15000", supportedModels());

        //Act
        int size = car.getRequiredComponentTypes().size();
        ComponentType first = car.getRequiredComponentTypes().get(0);
        ComponentType last = car.getRequiredComponentTypes().get(3);

        //Assert
        assertEquals(4, size);
        assertEquals(ComponentType.of("Wheels"), first);
        assertEquals(ComponentType.of("Interior"), last);
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

    private static Steering steering(String price, Set<ModelName> suitableModels) {
        return new Steering(Id.newId(), money(price), ComponentName.of("Steering"), suitableModels);
    }

    private static Transmission transmission(String value, String price, Set<ModelName> suitableModels) {
        return new Transmission(Id.newId(), money(price), ComponentName.of("Transmission"), suitableModels, TransmissionType.of(value));
    }

    private static Wheel wheel(String price, Set<ModelName> suitableModels) {
        return new Wheel(Id.newId(), money(price), ComponentName.of("Wheel"), suitableModels);
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
