package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractComponentEntity extends BaseJpaEntity {
    @Column(name = "price", nullable = false, precision = 12)
    private BigDecimal price;

    @Column(name = "component_name", nullable = false, length = 120)
    private String componentName;

    protected AbstractComponentEntity(UUID id, BigDecimal price, String componentName) {
        super(id);
        this.price = price;
        this.componentName = componentName;
    }
}
