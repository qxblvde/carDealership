package domain;

import org.junit.jupiter.api.Test;
import ru.sivak.domain.entities.Component;
import ru.sivak.domain.valueObjects.*;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComponentTest {
    @Test
    void isSuitableWith_returnsTrue() {
        ModelName model = ModelName.of("camry");
        Component component = Component.builder()
                .id(Id.newId())
                .name(ComponentName.of("wheel"))
                .type(ComponentType.of("wheels"))
                .price(Money.of(BigDecimal.valueOf(500)))
                .suitableModels(Set.of(model))
                .build();
        assertTrue(component.isSuitableWith(model));
    }
    @Test
    void isSuitableWith_returnsFalse() {
        Component component = Component.builder()
                .id(Id.newId())
                .name(ComponentName.of("wheel"))
                .type(ComponentType.of("wheels"))
                .price(Money.of(BigDecimal.valueOf(500)))
                .suitableModels(Set.of(ModelName.of("another")))
                .build();
        assertFalse(component.isSuitableWith(ModelName.of("camry")));
    }
}
