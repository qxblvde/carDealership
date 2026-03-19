package ru.sivak.application.mappers;

import ru.sivak.application.dto.BrandDto;
import ru.sivak.domain.entities.Brand;

public class BrandMapper {
    private BrandMapper() {}

    public static BrandDto toDto(Brand brand) {
        return new BrandDto(
                brand.getName(),
                brand.getPrice(),
                brand.getComponentName(),
                brand.getSuitableModels()
        );
    }
}
