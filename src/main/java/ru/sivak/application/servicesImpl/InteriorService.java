package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.InteriorDto;
import ru.sivak.application.mappers.InteriorMapper;
import ru.sivak.application.query.InteriorQuery;
import ru.sivak.application.services.IInteriorService;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.repositories.InteriorRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InteriorService implements IInteriorService {
    @NonNull
    private final InteriorRepository interiorRepository;

    @NonNull
    private final InteriorMapper interiorMapper;


    @Override
    public InteriorDto create(Interior interior) {
        interiorRepository.create(interior);
        return interiorMapper.map(interior);
    }

    
    public InteriorDto update(Interior interior) {
        interiorRepository.update(interior);
        return interiorMapper.map(interior);
    }

    @Override
    public void delete(Id id) {
        interiorRepository.delete(id);
    }

    @Override
    public List<InteriorDto> findAll() {
        return interiorRepository.findAll()
                .stream()
                .map(interiorMapper::map)
                .toList();
    }

    @Override
    public List<InteriorDto> query(InteriorQuery query) {
        return interiorRepository.query(query)
                .stream()
                .map(interiorMapper::map)
                .toList();
    }

    @Override
    public InteriorDto get(Id id) {
        return interiorRepository.find(id)
                .map(interiorMapper::map)
                .orElseThrow(() -> new RuntimeException("Interior not found"));
    }
}
