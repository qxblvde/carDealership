package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.CarDto;
import ru.sivak.application.mappers.CarMapper;
import ru.sivak.application.query.CarQuery;
import ru.sivak.application.services.ICarService;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService implements ICarService {
    @NonNull
    private final CarRepository repository;
    @NonNull
    private final CarMapper carMapper;

    @Override
    public CarDto create(@NonNull Car car) {
        repository.create(car);
        return carMapper.map(car);
    }

    @Override
    public CarDto update(@NonNull Car car) {
        repository.update(car);
        return carMapper.map(car);
    }

    @Override
    public void delete(@NonNull Id id) {
        repository.delete(id);
    }

    @Override
    public List<CarDto> findAll() {
        return repository.findAll()
                .stream()
                .map(carMapper::map)
                .toList();
    }

    @Override
    public List<CarDto> query(@NonNull CarQuery query) {
        return repository.query(query)
                .stream()
                .map(carMapper::map)
                .toList();
    }

    @Override
    public CarDto get(@NonNull Id id) {
        return repository.find(id)
                .map(carMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
    }
}
