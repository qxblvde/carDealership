package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.BodyDto;
import ru.sivak.application.mappers.BodyMapper;
import ru.sivak.application.query.BodyQuery;
import ru.sivak.application.services.IBodyService;
import ru.sivak.domain.entities.Body;
import ru.sivak.domain.repositories.BodyRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor

public class BodyService implements IBodyService {
    @NonNull
    private final BodyRepository bodyRepository;

    @Override
    public BodyDto save(Body body) {
        bodyRepository.save(body);
        return BodyMapper.toDto(body);
    }

    @Override
    public void delete(Id id) {
        bodyRepository.delete(id);
    }

    @Override
    public List<BodyDto> findAll() {
         return bodyRepository.findAll()
                .stream()
                .map(BodyMapper::toDto)
                .toList();
    }

    public List<BodyDto> query(BodyQuery query) {
        return bodyRepository.query(query)
                .stream()
                .map(BodyMapper::toDto)
                .toList();
    }

    @Override
    public BodyDto get(Id id) {
        return bodyRepository.find(id)
                .map(BodyMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Body not found"));
    }
}
