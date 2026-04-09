package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.ModelDto;
import ru.sivak.application.mappers.ModelMapper;
import ru.sivak.application.query.ModelQuery;
import ru.sivak.application.services.IModelService;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.repositories.ModelRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ModelService implements IModelService {
    @NonNull
    private final ModelRepository modelRepository;

    @NonNull
    private final ModelMapper modelMapper;

    @Override
    public ModelDto create(Model model) {
        modelRepository.create(model);
        return modelMapper.map(model);
    }

    
    public ModelDto update(Model model) {
        modelRepository.update(model);
        return modelMapper.map(model);
    }

    @Override
    public void delete(Id id) {
        modelRepository.delete(id);
    }

    @Override
    public List<ModelDto> findAll() {
        return modelRepository.findAll()
                .stream()
                .map(modelMapper::map)
                .toList();
    }

    @Override
    public List<ModelDto> query(ModelQuery query) {
        return modelRepository.query(query)
                .stream()
                .map(modelMapper::map)
                .toList();
    }

    @Override
    public ModelDto get(Id id) {
        return modelRepository.find(id)
                .map(modelMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Model not found"));
    }
}
