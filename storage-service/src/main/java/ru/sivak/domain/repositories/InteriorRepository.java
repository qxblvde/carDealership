package ru.sivak.domain.repositories;

import ru.sivak.application.query.InteriorQuery;
import ru.sivak.domain.entities.Interior;

import java.util.List;

public interface InteriorRepository extends Repository<Interior> {

    List<Interior> query(InteriorQuery query);

}