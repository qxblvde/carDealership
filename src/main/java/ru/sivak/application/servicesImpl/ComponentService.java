package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.ComponentDto;
import ru.sivak.application.mappers.ComponentMapper;
import ru.sivak.application.query.ComponentQuery;
import ru.sivak.application.services.IComponentService;
import ru.sivak.domain.entities.Component;
import ru.sivak.domain.repositories.ComponentRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;


@RequiredArgsConstructor
public class ComponentService implements IComponentService {
    @NonNull
    private final ComponentRepository repository;

    public ComponentDto save(@NonNull Component component) {
        repository.save(component);
        return ComponentMapper.toDto(component);
    }

    public void delete(@NonNull Id id) {
        repository.delete(id);
    }

    public ComponentDto get(@NonNull Id id) {
        return repository.find(id)
                .map(ComponentMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Component not found"));
    }

    public List<ComponentDto> query(@NonNull ComponentQuery query) {
        return repository.query(query)
                .stream()
                .map(ComponentMapper::toDto)
                .toList();
    }
}
