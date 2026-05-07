package ru.sivak.infrastructure.messaging.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.domain.entities.AssemblyOrder;
import ru.sivak.domain.entities.CarStock;
import ru.sivak.domain.repositories.AssemblyOrderRepository;
import ru.sivak.domain.repositories.CarStockRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.messaging.config.RabbitMQConfig;
import ru.sivak.integration.messaging.event.OrderApprovedEvent;
import ru.sivak.integration.messaging.event.OrderRejectedEvent;
import ru.sivak.integration.messaging.event.OrderSentForApprovalEvent;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderSentForApprovalListener {

    private final AssemblyOrderRepository assemblyOrderRepository;
    private final CarStockRepository carStockRepository;
    private final RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.ORDER_SENT_FOR_APPROVAL_QUEUE)
    @Transactional
    public void handle(OrderSentForApprovalEvent event) {
        log.info("Received OrderSentForApproval orderId={} traceId={}", event.getOrderId(), event.getTraceId());

        if (assemblyOrderRepository.findBySourceOrderId(event.getOrderId()).isPresent()) {
            log.warn("Duplicate event ignored orderId={} traceId={}", event.getOrderId(), event.getTraceId());
            return;
        }

        boolean carAvailable = false;
        if (event.getCarId() != null) {
            Optional<CarStock> stock = carStockRepository.findByCarId(event.getCarId());
            if (stock.isPresent() && stock.get().isAvailable()) {
                stock.get().reserve();
                carStockRepository.update(stock.get());
                carAvailable = true;
            }
        }

        AssemblyOrder assemblyOrder = new AssemblyOrder(
                Id.newId(),
                event.getOrderId(),
                event.getOrderType(),
                event.getCarId(),
                null,
                Collections.emptySet()
        );

        if (carAvailable) {
            assemblyOrder.markAssembled();
            assemblyOrderRepository.create(assemblyOrder);

            OrderApprovedEvent approved = OrderApprovedEvent.builder()
                    .orderId(event.getOrderId())
                    .traceId(event.getTraceId())
                    .build();
            rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ROUTING_KEY_APPROVED, approved);
            log.info("Order approved orderId={} traceId={}", event.getOrderId(), event.getTraceId());
        } else {
            assemblyOrder.markFail();
            assemblyOrderRepository.create(assemblyOrder);

            OrderRejectedEvent rejected = OrderRejectedEvent.builder()
                    .orderId(event.getOrderId())
                    .traceId(event.getTraceId())
                    .build();
            rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_EXCHANGE, RabbitMQConfig.ROUTING_KEY_REJECTED, rejected);
            log.info("Order rejected orderId={} traceId={}", event.getOrderId(), event.getTraceId());
        }
    }
}
