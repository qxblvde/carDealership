package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fuels")
@Getter
@Setter
@NoArgsConstructor
public class FuelEntity extends AbstractComponentEntity {
    @Column(name = "fuel_type", nullable = false, length = 120)
    private String fuelType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "fuel_suitable_models",
            joinColumns = @JoinColumn(name = "fuel_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CarModelEntity> suitableModels = new HashSet<>();
}
