package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.EngineDto;
import ru.sivak.application.mappers.EngineMapper;
import ru.sivak.application.query.EngineQuery;
import ru.sivak.application.services.IEngineService;

import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.repositories.EngineRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
public class EngineService implements IEngineService {
    @NonNull
    private final EngineRepository engineRepository;
    
    @Override
    public EngineDto save(Engine engine) {
        engineRepository.save(engine);
        return EngineMapper.toDto(engine);
    }

    @Override
    public void delete(Id id) {
        engineRepository.delete(id);
    }

    @Override
    public List<EngineDto> findAll() {
        return engineRepository.findAll()
                .stream()
                .map(EngineMapper::toDto)
                .toList();
    }

    @Override
    public List<EngineDto> query(EngineQuery query) {
        return engineRepository.query(query)
                .stream()
                .map(EngineMapper::toDto)
                .toList();
    }

    @Override
    public EngineDto get(Id id) {
        return engineRepository.find(id)
                .map(EngineMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Engine not found"));
    }
}
