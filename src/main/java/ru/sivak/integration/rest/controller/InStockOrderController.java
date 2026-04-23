package ru.sivak.integration.rest.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.services.IInStockOrderService;
import ru.sivak.integration.rest.dto.CreateOrderRequest;
import ru.sivak.integration.rest.dto.UpdateOrderRequest;
import ru.sivak.integration.rest.mapper.InStockOrderRequestMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders/in-stock")
@RequiredArgsConstructor
public class InStockOrderController {
    private final IInStockOrderService inStockOrderService;
    private final InStockOrderRequestMapper inStockOrderRequestMapper;

    @PostMapping
    public OrderDto create(@RequestBody CreateOrderRequest request) {
        InStockOrderRequestMapper.CreateCommand command = inStockOrderRequestMapper.toCreateCommand(request);
        return inStockOrderService.create(command.managerId(), command.carId());
    }

    @GetMapping
    public List<OrderDto> query(
            @RequestParam(required = false) UUID clientId,
            @RequestParam(required = false) UUID managerId,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return inStockOrderService.query(inStockOrderRequestMapper.toQuery(clientId, managerId, state, minPrice, maxPrice));
    }

    @GetMapping("/{id}")
    public OrderDto getById(@PathVariable UUID id) {
        return inStockOrderService.getDto(inStockOrderRequestMapper.toId(id));
    }

    @PutMapping("/{id}")
    public OrderDto update(@PathVariable UUID id, @RequestBody UpdateOrderRequest request) {
        InStockOrderRequestMapper.UpdateCommand command = inStockOrderRequestMapper.toUpdateCommand(id, request);
        return inStockOrderService.update(command.orderId(), command.carId());
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Void> approve(@PathVariable UUID id) {
        inStockOrderService.approve(inStockOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/request-payment")
    public ResponseEntity<Void> requestPayment(@PathVariable UUID id) {
        inStockOrderService.requestPayment(inStockOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/pay")
    public ResponseEntity<Void> pay(@PathVariable UUID id) {
        inStockOrderService.pay(inStockOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/ready")
    public ResponseEntity<Void> markAsReady(@PathVariable UUID id) {
        inStockOrderService.markAsReady(inStockOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/complete")
    public ResponseEntity<Void> complete(@PathVariable UUID id) {
        inStockOrderService.complete(inStockOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancel(@PathVariable UUID id) {
        inStockOrderService.cancel(inStockOrderRequestMapper.toId(id));
        return ResponseEntity.noContent().build();
    }
}
