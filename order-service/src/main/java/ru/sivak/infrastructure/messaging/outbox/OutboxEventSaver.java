package ru.sivak.infrastructure.messaging.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import ru.sivak.infrastructure.persistence.entity.OutboxEventEntity;
import ru.sivak.infrastructure.persistence.repository.OutboxEventEntityJpaRepository;

import ru.sivak.integration.messaging.event.DomainEvent;

import java.time.Instant;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OutboxEventSaver {

    private final OutboxEventEntityJpaRepository outboxRepository;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public void save(@NonNull DomainEvent event, @NonNull String eventType, @NonNull String traceId) {
        OutboxEventEntity entity = OutboxEventEntity.builder()
                .id(UUID.randomUUID())
                .eventType(eventType)
                .payload(objectMapper.writeValueAsString(event))
                .traceId(traceId)
                .createdAt(Instant.now())
                .published(false)
                .build();
        outboxRepository.save(entity);
    }
}
