package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.InteriorDto;
import ru.sivak.application.mappers.InteriorMapper;
import ru.sivak.application.query.InteriorQuery;
import ru.sivak.application.services.IInteriorService;
import ru.sivak.domain.entities.Interior;
import ru.sivak.domain.repositories.InteriorRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
public class InteriorService implements IInteriorService {
    @NonNull
    private final InteriorRepository interiorRepository;


    @Override
    public InteriorDto save(Interior interior) {
        interiorRepository.save(interior);
        return InteriorMapper.toDto(interior);
    }

    @Override
    public void delete(Id id) {
        interiorRepository.delete(id);
    }

    @Override
    public List<InteriorDto> findAll() {
        return interiorRepository.findAll()
                .stream()
                .map(InteriorMapper::toDto)
                .toList();
    }

    @Override
    public List<InteriorDto> query(InteriorQuery query) {
        return interiorRepository.query(query)
                .stream()
                .map(InteriorMapper::toDto)
                .toList();
    }

    @Override
    public InteriorDto get(Id id) {
        return interiorRepository.find(id)
                .map(InteriorMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Interior not found"));
    }
}