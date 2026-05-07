package ru.sivak.integration.rest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sivak.application.dto.CarDto;
import ru.sivak.application.services.ICarConfiguratorService;
import ru.sivak.application.services.ICarService;
import ru.sivak.integration.rest.dto.CreateCarRequest;
import ru.sivak.integration.rest.mapper.CarRequestMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {
    private final ICarService carService;
    private final ICarConfiguratorService carConfiguratorService;
    private final CarRequestMapper carRequestMapper;

    @PostMapping
    public CarDto create(@RequestBody CreateCarRequest request) {
        CarRequestMapper.CreateCar command = carRequestMapper.toCreateCar(request);
        return carConfiguratorService.createFromComponents(
                command.bodyId(),
                command.brandId(),
                command.colorId(),
                command.driveId(),
                command.engineId(),
                command.fuelId(),
                command.modelId(),
                command.transmissionId(),
                command.steeringId(),
                command.wheelId(),
                command.interiorId()
        );
    }

    @GetMapping
    public List<CarDto> query(
            @RequestParam(required = false) String brandName,
            @RequestParam(required = false) String modelName,
            @RequestParam(required = false) String bodyType,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String driveType,
            @RequestParam(required = false) Integer enginePower,
            @RequestParam(required = false) Integer engineVolume,
            @RequestParam(required = false) String fuelType,
            @RequestParam(required = false) String transmissionType,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        return carService.query(carRequestMapper.toQuery(
                brandName,
                modelName,
                bodyType,
                color,
                driveType,
                enginePower,
                engineVolume,
                fuelType,
                transmissionType,
                minPrice,
                maxPrice
        ));
    }

    @GetMapping("/{id}")
    public CarDto getById(@PathVariable UUID id) {
        return carService.get(carRequestMapper.toId(id));
    }
}
