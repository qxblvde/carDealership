package ru.sivak.integration.rest.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sivak.application.dto.AssemblyOrderDto;
import ru.sivak.application.services.IAssemblyOrderService;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.integration.rest.dto.CreateAssemblyOrderRequest;
import ru.sivak.integration.rest.dto.UpdateAssemblyOrderRequest;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/assembly-orders")
@RequiredArgsConstructor
public class AssemblyOrderController {

    private final IAssemblyOrderService assemblyOrderService;

    @PostMapping
    public ResponseEntity<AssemblyOrderDto> create(@Valid @RequestBody CreateAssemblyOrderRequest request) {
        AssemblyOrderDto dto = assemblyOrderService.create(
                request.sourceOrderType(),
                request.sourceOrderId(),
                request.carId(),
                request.warehouseEmployeeId(),
                request.requiredComponentIds()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping
    public List<AssemblyOrderDto> findAll() {
        return assemblyOrderService.findAll();
    }

    @GetMapping("/{id}")
    public AssemblyOrderDto getById(@PathVariable UUID id) {
        return assemblyOrderService.get(Id.of(id));
    }

    @PutMapping("/{id}")
    public AssemblyOrderDto update(@PathVariable UUID id, @Valid @RequestBody UpdateAssemblyOrderRequest request) {
        return assemblyOrderService.update(Id.of(id), request.status());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        assemblyOrderService.delete(Id.of(id));
        return ResponseEntity.noContent().build();
    }
}
