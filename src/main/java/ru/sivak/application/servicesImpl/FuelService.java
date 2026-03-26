package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.FuelDto;
import ru.sivak.application.mappers.FuelMapper;
import ru.sivak.application.query.FuelQuery;
import ru.sivak.application.services.IFuelService;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.repositories.FuelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
@Service
public class FuelService implements IFuelService {
    @NonNull
    private final FuelRepository FuelRepository;
    @NonNull
    private final FuelMapper fuelMapper;

    @Override
    public FuelDto create(Fuel fuel) {
        FuelRepository.create(fuel);
        return fuelMapper.map(fuel);
    }

    
    public FuelDto update(Fuel fuel) {
        FuelRepository.update(fuel);
        return fuelMapper.map(fuel);
    }

    @Override
    public void delete(Id id) {
        FuelRepository.delete(id);
    }

    @Override
    public List<FuelDto> findAll() {
        return FuelRepository.findAll()
                .stream()
                .map(fuelMapper::map)
                .toList();
    }

    @Override
    public List<FuelDto> query(FuelQuery query) {
        return FuelRepository.query(query)
                .stream()
                .map(fuelMapper::map)
                .toList();
    }

    @Override
    public FuelDto get(Id id) {
        return FuelRepository.find(id)
                .map(fuelMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Fuel not found"));
    }
}
