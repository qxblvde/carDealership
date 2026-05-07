package ru.sivak.infrastructure.messaging.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.infrastructure.messaging.config.RabbitMQConfig;
import ru.sivak.infrastructure.persistence.entity.OutboxEventEntity;
import ru.sivak.infrastructure.persistence.repository.OutboxEventEntityJpaRepository;
import ru.sivak.integration.messaging.event.OrderSentForApprovalEvent;

import java.time.Instant;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxEventEntityJpaRepository outboxRepository;
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelayString = "${outbox.publisher.delay-ms:5000}")
    @Transactional
    public void publish() {
        List<OutboxEventEntity> unpublished = outboxRepository.findAllByPublishedFalseOrderByCreatedAtAsc();

        for (OutboxEventEntity event : unpublished) {
            try {
                sendEvent(event);
                event.setPublished(true);
                event.setPublishedAt(Instant.now());
                outboxRepository.save(event);
            } catch (Exception e) {
                log.error("Failed to publish outbox event id={} type={}", event.getId(), event.getEventType(), e);
            }
        }
    }

    @SneakyThrows
    private void sendEvent(OutboxEventEntity event) {
        Object payload = deserializePayload(event);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_EXCHANGE,
                RabbitMQConfig.ROUTING_KEY_SENT_FOR_APPROVAL,
                payload
        );
        log.info("Published outbox event id={} type={} traceId={}", event.getId(), event.getEventType(), event.getTraceId());
    }

    @SneakyThrows
    private Object deserializePayload(OutboxEventEntity event) {
        return switch (event.getEventType()) {
            case "OrderSentForApproval" -> objectMapper.readValue(event.getPayload(), OrderSentForApprovalEvent.class);
            default -> throw new IllegalArgumentException("Unknown event type: " + event.getEventType());
        };
    }
}
