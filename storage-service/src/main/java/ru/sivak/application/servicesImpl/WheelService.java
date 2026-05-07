package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.WheelDto;
import ru.sivak.application.mappers.WheelMapper;
import ru.sivak.application.query.WheelQuery;
import ru.sivak.application.services.IWheelService;
import ru.sivak.domain.entities.Wheel;
import ru.sivak.domain.repositories.WheelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
@Service
public class WheelService implements IWheelService {

    @NonNull
    private final WheelRepository wheelRepository;

    @NonNull
    private final WheelMapper wheelMapper;

    @Override
    public WheelDto create(Wheel wheel) {
        wheelRepository.create(wheel);
        return wheelMapper.map(wheel);
    }

    
    public WheelDto update(Wheel wheel) {
        wheelRepository.update(wheel);
        return wheelMapper.map(wheel);
    }

    @Override
    public void delete(Id id) {
        wheelRepository.delete(id);
    }

    @Override
    public List<WheelDto> findAll() {
        return wheelRepository.findAll()
                .stream()
                .map(wheelMapper::map)
                .toList();
    }

    @Override
    public List<WheelDto> query(WheelQuery query) {
        return wheelRepository.query(query)
                .stream()
                .map(wheelMapper::map)
                .toList();
    }

    @Override
    public WheelDto get(Id id) {
        return wheelRepository.find(id)
                .map(wheelMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Transmission not found"));
    }
}
