package ru.sivak.application.services;

import ru.sivak.application.dto.ModelDto;
import ru.sivak.application.query.ModelQuery;
import ru.sivak.domain.entities.Model;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IModelService {
    ModelDto save(Model model);

    void delete(Id id);

    List<ModelDto> findAll();

    List<ModelDto> query(ModelQuery query);

    ModelDto get(Id id);
}
