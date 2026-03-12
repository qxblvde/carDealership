package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.FuelDto;
import ru.sivak.application.mappers.FuelMapper;
import ru.sivak.application.query.FuelQuery;
import ru.sivak.application.services.IFuelService;
import ru.sivak.domain.entities.Fuel;
import ru.sivak.domain.repositories.FuelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
public class FuelService implements IFuelService {
    @NonNull
    private final FuelRepository FuelRepository;

    @Override
    public FuelDto save(Fuel fuel) {
        FuelRepository.save(fuel);
        return FuelMapper.toDto(fuel);
    }

    @Override
    public void delete(Id id) {
        FuelRepository.delete(id);
    }

    @Override
    public List<FuelDto> findAll() {
        return FuelRepository.findAll()
                .stream()
                .map(FuelMapper::toDto)
                .toList();
    }

    @Override
    public List<FuelDto> query(FuelQuery query) {
        return FuelRepository.query(query)
                .stream()
                .map(FuelMapper::toDto)
                .toList();
    }

    @Override
    public FuelDto get(Id id) {
        return FuelRepository.find(id)
                .map(FuelMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Fuel not found"));
    }
}
