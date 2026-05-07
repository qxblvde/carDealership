package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "car_stock")
@Getter
@Setter
@NoArgsConstructor
public class CarStockEntity extends BaseJpaEntity {

    @Column(name = "car_id", nullable = false, unique = true)
    private UUID carId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "reserved", nullable = false)
    private int reserved;
}
