package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.WheelDto;
import ru.sivak.application.mappers.WheelMapper;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.application.services.IWheelService;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.repositories.WheelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
public class WheelService implements IWheelService {

    @NonNull
    private final WheelRepository wheelRepository;

    @Override
    public WheelDto save(Wheel wheel) {
        wheelRepository.save(wheel);
        return WheelMapper.toDto(wheel);
    }

    @Override
    public void delete(Id id) {
        wheelRepository.delete(id);
    }

    @Override
    public List<WheelDto> findAll() {
        return wheelRepository.findAll()
                .stream()
                .map(WheelMapper::toDto)
                .toList();
    }

    @Override
    public List<WheelDto> query(WheelQuery query) {
        return wheelRepository.query(query)
                .stream()
                .map(WheelMapper::toDto)
                .toList();
    }

    @Override
    public WheelDto get(Id id) {
        return wheelRepository.find(id)
                .map(WheelMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Transmission not found"));
    }
}
