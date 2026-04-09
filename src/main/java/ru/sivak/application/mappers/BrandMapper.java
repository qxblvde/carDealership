package ru.sivak.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.sivak.application.dto.BrandDto;
import ru.sivak.domain.entities.Brand;

@Mapper(componentModel = "spring")
public interface BrandMapper {

    @Mapping(target = "brandName", source = "name")
    BrandDto map(Brand brand);
}
