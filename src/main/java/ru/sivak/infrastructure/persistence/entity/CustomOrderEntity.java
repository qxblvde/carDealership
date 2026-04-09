package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "custom_orders")
@NoArgsConstructor
public class CustomOrderEntity extends AbstractOrderEntity {
}
