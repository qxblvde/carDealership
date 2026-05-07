package ru.sivak.infrastructure.messaging.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.repositories.CustomOrderRepository;
import ru.sivak.domain.repositories.InStockOrderRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.messaging.config.RabbitMQConfig;
import ru.sivak.integration.messaging.event.OrderRejectedEvent;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderRejectedListener {

    private final InStockOrderRepository inStockOrderRepository;
    private final CustomOrderRepository customOrderRepository;

    @RabbitListener(queues = RabbitMQConfig.ORDER_REJECTED_QUEUE)
    @Transactional
    public void handle(OrderRejectedEvent event) {
        log.info("Received OrderRejected orderId={} traceId={}", event.getOrderId(), event.getTraceId());

        Id orderId = Id.of(event.getOrderId());

        Optional<InStockOrder> inStockOrder = inStockOrderRepository.find(orderId);
        if (inStockOrder.isPresent()) {
            InStockOrder order = inStockOrder.get();
            if (order.cancel()) {
                inStockOrderRepository.update(order);
                log.info("InStockOrder cancelled orderId={} traceId={}", event.getOrderId(), event.getTraceId());
            } else {
                log.warn("InStockOrder cancel rejected orderId={} traceId={}", event.getOrderId(), event.getTraceId());
            }
            return;
        }

        Optional<CustomOrder> customOrder = customOrderRepository.find(orderId);
        if (customOrder.isPresent()) {
            CustomOrder order = customOrder.get();
            if (order.cancel()) {
                customOrderRepository.update(order);
                log.info("CustomOrder cancelled orderId={} traceId={}", event.getOrderId(), event.getTraceId());
            } else {
                log.warn("CustomOrder cancel rejected orderId={} traceId={}", event.getOrderId(), event.getTraceId());
            }
            return;
        }

        log.error("Order not found for rejection event orderId={} traceId={}", event.getOrderId(), event.getTraceId());
    }
}
