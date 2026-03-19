package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.CarDto;
import ru.sivak.application.mappers.CarMapper;
import ru.sivak.application.query.CarQuery;
import ru.sivak.application.services.ICarService;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
public class CarService implements ICarService {
    @NonNull
    private final CarRepository repository;

    public CarDto save(@NonNull Car car) {
        repository.save(car);
        return CarMapper.toDto(car);
    }

    public void delete(@NonNull Id id) {
        repository.delete(id);
    }

    public List<CarDto> findAll() {
        return repository.findAll()
                .stream()
                .map(CarMapper::toDto)
                .toList();
    }

    public List<CarDto> query(@NonNull CarQuery query) {
        return repository.query(query)
                .stream()
                .map(CarMapper::toDto)
                .toList();
    }

    public CarDto get(@NonNull Id id) {
        return repository.find(id)
                .map(CarMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
    }
}
