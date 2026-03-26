package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.BodyDto;
import ru.sivak.application.mappers.BodyMapper;
import ru.sivak.application.query.BodyQuery;
import ru.sivak.application.services.IBodyService;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.repositories.BodyRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BodyService implements IBodyService {
    @NonNull
    private final BodyRepository bodyRepository;

    @NonNull
    private final BodyMapper bodyMapper;

    @Override
    public BodyDto create(Body body) {
        bodyRepository.create(body);
        return bodyMapper.map(body);
    }

    
    public BodyDto update(Body body) {
        bodyRepository.update(body);
        return bodyMapper.map(body);
    }

    @Override
    public void delete(Id id) {
        bodyRepository.delete(id);
    }

    @Override
    public List<BodyDto> findAll() {
         return bodyRepository.findAll()
                .stream()
                .map(bodyMapper::map)
                .toList();
    }

    public List<BodyDto> query(BodyQuery query) {
        return bodyRepository.query(query)
                .stream()
                .map(bodyMapper::map)
                .toList();
    }

    @Override
    public BodyDto get(Id id) {
        return bodyRepository.find(id)
                .map(bodyMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Body not found"));
    }
}
