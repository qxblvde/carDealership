package ru.sivak.domain.repositories;

import ru.sivak.application.query.BrandQuery;
import ru.sivak.domain.entities.Brand;

import java.util.List;

public interface BrandRepository extends Repository<Brand> {
    List<Brand> query(BrandQuery query);
}
