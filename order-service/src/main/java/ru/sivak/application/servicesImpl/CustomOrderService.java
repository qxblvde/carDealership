package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.mappers.OrderMapper;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.application.services.ICustomOrderService;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.repositories.CustomOrderRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;
import ru.sivak.infrastructure.messaging.outbox.OutboxEventSaver;
import ru.sivak.infrastructure.security.AuthenticatedUserService;
import ru.sivak.integration.messaging.event.OrderSentForApprovalEvent;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CustomOrderService implements ICustomOrderService {
    @NonNull
    private final CustomOrderRepository customOrderRepository;
    @NonNull
    private final OrderMapper orderMapper;
    @NonNull
    private final OutboxEventSaver outboxEventSaver;
    @NonNull
    private final AuthenticatedUserService authenticatedUserService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public OrderDto create(@NonNull Id managerId, @NonNull Id carId, @NonNull Money price) {
        Id clientId = authenticatedUserService.getCurrentUserId();
        CustomOrder order = new CustomOrder(Id.newId(), managerId, clientId, carId, price);
        customOrderRepository.create(order);
        return orderMapper.map(order);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public List<OrderDto> query(@NonNull CustomOrderQuery query) {
        CustomOrderQuery dublicate = query;
        if (!authenticatedUserService.hasRole("MANAGER") && !authenticatedUserService.hasRole("ADMIN")) {
            dublicate = query.toBuilder()
                    .clientId(authenticatedUserService.getCurrentUserId())
                    .build();
        }
        return customOrderRepository.query(dublicate)
                .stream()
                .map(orderMapper::map)
                .toList();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void approve(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.approve()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void requestPayment(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.requestPayment()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentCustomOrderOwner(#orderId))")
    @Transactional
    public void pay(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.pay()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);

        String traceId = UUID.randomUUID().toString();
        OrderSentForApprovalEvent event = OrderSentForApprovalEvent.builder()
                .orderId(order.getId().getId())
                .orderType("CUSTOM")
                .carId(order.getCarId().getId())
                .traceId(traceId)
                .build();
        outboxEventSaver.save(event, "OrderSentForApproval", traceId);
        log.info("CustomOrder paid and sent for approval orderId={} traceId={}", orderId.getId(), traceId);
    }

    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public void requestDelivery(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.requestDelivery()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void complete(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.complete()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentCustomOrderOwner(#orderId))")
    public void cancel(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.cancel()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public void markAsReady(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.markAsReady()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    public CustomOrder get(@NonNull Id id) {
        return customOrderRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or (hasRole('USER') and @orderAccessChecker.isCurrentCustomOrderOwner(#id))")
    public OrderDto getDto(@NonNull Id id) {
        return orderMapper.map(get(id));
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentCustomOrderOwner(#orderId))")
    public OrderDto update(@NonNull Id orderId, @NonNull Id newClientId, @NonNull Id newCarId, @NonNull Money newPrice) {
        CustomOrder order = get(orderId);
        order.updateClient(newClientId);
        order.updateCar(newCarId, newPrice);
        customOrderRepository.update(order);
        return orderMapper.map(order);
    }
}
