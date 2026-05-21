package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.application.mappers.AvailableCarMapper;
import ru.sivak.application.services.IAvailableCarService;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.entities.CarStock;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.repositories.CarStockRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class AvailableCarService implements IAvailableCarService {
    @NonNull
    private final CarStockRepository carStockRepository;
    @NonNull
    private final CarRepository carRepository;
    @NonNull
    private final AvailableCarMapper availableCarMapper;

    public List<AvailableCarDto> findAllAvailable() {
        return carStockRepository.findAll()
                .stream()
                .filter(CarStock::isAvailable)
                .map(this::findAvailableCarByStock)
                .flatMap(Optional::stream)
                .toList();
    }

    public Optional<AvailableCarDto> findAvailable(@NonNull Id id) {
        Optional<CarStock> stock = carStockRepository.findByCarId(id.getId())
                .filter(CarStock::isAvailable);
        Optional<Car> car = carRepository.find(id);
        if (stock.isEmpty() || car.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(availableCarMapper.map(car.get(), stock.get()));
    }

    private Optional<AvailableCarDto> findAvailableCarByStock(CarStock stock) {
        Optional<Car> car = carRepository.find(Id.of(stock.getCarId()));
        if (car.isEmpty()) {
            log.warn("CarStock references missing car carId={}", stock.getCarId());
            return Optional.empty();
        }
        return Optional.of(availableCarMapper.map(car.get(), stock));
    }
}
