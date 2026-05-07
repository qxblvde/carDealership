package ru.sivak.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "test_drive_requests")
@Getter
@Setter
@NoArgsConstructor
public class TestDriveRequestEntity extends BaseJpaEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private AppUserEntity client;

    @Column(name = "car_id", nullable = false)
    private UUID carId;

    @Column(name = "scheduled_time", nullable = false)
    private LocalDate scheduledTime;

}
