package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "car_models")
@Getter
@Setter
@NoArgsConstructor
public class CarModelEntity extends BaseJpaEntity {
    @Column(name = "model_name", nullable = false, unique = true, length = 120)
    private String modelName;

    @Column(name = "price", nullable = false, precision = 12, scale = 0)
    private BigDecimal price;

    @Column(name = "component_name", nullable = false, length = 120)
    private String componentName;
}
