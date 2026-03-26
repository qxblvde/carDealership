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
@Table(name = "engines")
@Getter
@Setter
@NoArgsConstructor
public class EngineEntity extends AbstractComponentEntity {
    @Column(name = "engine_power", nullable = false)
    private Integer enginePower;

    @Column(name = "engine_volume", nullable = false)
    private Integer engineVolume;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "engine_suitable_models",
            joinColumns = @JoinColumn(name = "engine_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CarModelEntity> suitableModels = new HashSet<>();
}
