package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.EngineDto;
import ru.sivak.application.mappers.EngineMapper;
import ru.sivak.application.query.EngineQuery;
import ru.sivak.application.services.IEngineService;

import ru.sivak.domain.entities.Engine;
import ru.sivak.domain.repositories.EngineRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
@Service
public class EngineService implements IEngineService {
    @NonNull
    private final EngineRepository engineRepository;

    @NonNull
    private final EngineMapper engineMapper;
    
    @Override
    public EngineDto create(Engine engine) {
        engineRepository.create(engine);
        return engineMapper.map(engine);
    }

    
    public EngineDto update(Engine engine) {
        engineRepository.update(engine);
        return engineMapper.map(engine);
    }

    @Override
    public void delete(Id id) {
        engineRepository.delete(id);
    }

    @Override
    public List<EngineDto> findAll() {
        return engineRepository.findAll()
                .stream()
                .map(engineMapper::map)
                .toList();
    }

    @Override
    public List<EngineDto> query(EngineQuery query) {
        return engineRepository.query(query)
                .stream()
                .map(engineMapper::map)
                .toList();
    }

    @Override
    public EngineDto get(Id id) {
        return engineRepository.find(id)
                .map(engineMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Engine not found"));
    }
}
