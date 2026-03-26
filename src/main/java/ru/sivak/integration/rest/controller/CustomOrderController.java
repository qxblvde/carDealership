package ru.sivak.integration.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.services.ICustomOrderService;
import ru.sivak.integration.rest.dto.CreateOrderRequest;
import ru.sivak.integration.rest.dto.UpdateOrderRequest;
import ru.sivak.integration.rest.mapper.CustomOrderRequestMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders/custom")
@RequiredArgsConstructor
public class CustomOrderController {
    private final ICustomOrderService customOrderService;
    private final CustomOrderRequestMapper customOrderRequestMapper;

    @PostMapping
    public OrderDto create(@RequestBody CreateOrderRequest request) {
        CustomOrderRequestMapper.CreateCommand command = customOrderRequestMapper.toCreateCommand(request);
        return customOrderService.create(command.managerId(), command.clientId(), command.carId());
    }

    @GetMapping
    public List<OrderDto> query(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) UUID managerId,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return customOrderService.query(customOrderRequestMapper.toQuery(clientId, managerId, state, minPrice, maxPrice));
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable UUID id) {
        return customOrderService.getDto(customOrderRequestMapper.toId(id));
    }

    @PutMapping("/{id}")
    public OrderDto update(@PathVariable UUID id, @RequestBody UpdateOrderRequest request) {
        CustomOrderRequestMapper.UpdateCommand command = customOrderRequestMapper.toUpdateCommand(id, request);
        return customOrderService.update(command.orderId(), command.clientId(), command.carId());
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable UUID id) {
        customOrderService.approve(customOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/request-payment")
    public ResponseEntity<Void> requestPayment(@PathVariable UUID id) {
        customOrderService.requestPayment(customOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Void> pay(@PathVariable UUID id) {
        customOrderService.pay(customOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/request-delivery")
    public ResponseEntity<Void> requestDelivery(@PathVariable UUID id) {
        customOrderService.requestDelivery(customOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ready")
    public ResponseEntity<Void> markAsReady(@PathVariable UUID id) {
        customOrderService.markAsReady(customOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable UUID id) {
        customOrderService.complete(customOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        customOrderService.cancel(customOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }
}
