package ru.sivak.integration.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.application.services.IAvailableCarService;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/cars")
@RequiredArgsConstructor
public class AvailableCarController {
    private final IAvailableCarService availableCarService;

    @GetMapping
    public List<AvailableCarDto> findAll() {
        return availableCarService.findAll();
    }

    @GetMapping("/{id}")
    public AvailableCarDto get(@PathVariable UUID id) {
        return availableCarService.get(Id.of(id));
    }
}
