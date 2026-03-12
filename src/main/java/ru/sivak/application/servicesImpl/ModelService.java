package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.ModelDto;
import ru.sivak.application.mappers.ModelMapper;
import ru.sivak.application.query.ModelQuery;
import ru.sivak.application.services.IModelService;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.repositories.ModelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
public class ModelService implements IModelService {
    @NonNull
    private final ModelRepository modelRepository;

    @Override
    public ModelDto save(Model model) {
        modelRepository.save(model);
        return ModelMapper.toDto(model);
    }

    @Override
    public void delete(Id id) {
        modelRepository.delete(id);
    }

    @Override
    public List<ModelDto> findAll() {
        return modelRepository.findAll()
                .stream()
                .map(ModelMapper::toDto)
                .toList();
    }

    @Override
    public List<ModelDto> query(ModelQuery query) {
        return modelRepository.query(query)
                .stream()
                .map(ModelMapper::toDto)
                .toList();
    }

    @Override
    public ModelDto get(Id id) {
        return modelRepository.find(id)
                .map(ModelMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Model not found"));
    }
}
