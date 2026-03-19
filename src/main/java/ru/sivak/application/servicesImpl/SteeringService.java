package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.SteeringDto;
import ru.sivak.application.mappers.SteeringMapper;
import ru.sivak.application.query.SteeringQuery;
import ru.sivak.application.services.ISteeringService;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.repositories.SteeringRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
public class SteeringService implements ISteeringService {

    @NonNull
    private final SteeringRepository steeringRepository;

    @Override
    public SteeringDto save(Steering steering) {
        steeringRepository.save(steering);
        return SteeringMapper.toDto(steering);
    }

    @Override
    public void delete(Id id) {
        steeringRepository.delete(id);
    }

    @Override
    public List<SteeringDto> findAll() {
        return steeringRepository.findAll()
                .stream()
                .map(SteeringMapper::toDto)
                .toList();
    }

    @Override
    public List<SteeringDto> query(SteeringQuery query) {
        return steeringRepository.query(query)
                .stream()
                .map(SteeringMapper::toDto)
                .toList();
    }

    @Override
    public SteeringDto get(Id id) {
        return steeringRepository.find(id)
                .map(SteeringMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Steering not found"));
    }
}