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
@Table(name = "transmissions")
@Getter
@Setter
@NoArgsConstructor
public class TransmissionEntity extends AbstractComponentEntity {
    @Column(name = "transmission_type", nullable = false, length = 120)
    private String transmissionType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "transmission_suitable_models",
            joinColumns = @JoinColumn(name = "transmission_id"),
            inverseJoinColumns = @JoinColumn(name = "model_id")
    )
    private Set<CarModelEntity> suitableModels = new HashSet<>();
}
