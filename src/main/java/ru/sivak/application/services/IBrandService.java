package ru.sivak.application.services;

import ru.sivak.application.dto.BrandDto;
import ru.sivak.application.query.BrandQuery;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

public interface IBrandService {
    BrandDto save(Brand brand);

    void delete(Id id);

    List<BrandDto> findAll();

    List<BrandDto> query(BrandQuery query);

    BrandDto get(Id id);
}

