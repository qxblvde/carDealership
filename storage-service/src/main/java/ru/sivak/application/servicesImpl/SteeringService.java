package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.SteeringDto;
import ru.sivak.application.mappers.SteeringMapper;
import ru.sivak.application.query.SteeringQuery;
import ru.sivak.application.services.ISteeringService;
import ru.sivak.domain.entities.Steering;
import ru.sivak.domain.repositories.SteeringRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SteeringService implements ISteeringService {

    @NonNull
    private final SteeringRepository steeringRepository;

    @NonNull
    private final SteeringMapper steeringMapper;

    @Override
    public SteeringDto create(Steering steering) {
        steeringRepository.create(steering);
        return steeringMapper.map(steering);
    }

    
    public SteeringDto update(Steering steering) {
        steeringRepository.update(steering);
        return steeringMapper.map(steering);
    }

    @Override
    public void delete(Id id) {
        steeringRepository.delete(id);
    }

    @Override
    public List<SteeringDto> findAll() {
        return steeringRepository.findAll()
                .stream()
                .map(steeringMapper::map)
                .toList();
    }

    @Override
    public List<SteeringDto> query(SteeringQuery query) {
        return steeringRepository.query(query)
                .stream()
                .map(steeringMapper::map)
                .toList();
    }

    @Override
    public SteeringDto get(Id id) {
        return steeringRepository.find(id)
                .map(steeringMapper::map)
                .orElseThrow(() -> new RuntimeException("Steering not found"));
    }
}
