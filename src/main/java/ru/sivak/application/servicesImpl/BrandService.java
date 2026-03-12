package ru.sivak.application.servicesImpl;

import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.BrandDto;
import ru.sivak.application.mappers.BrandMapper;
import ru.sivak.application.query.BrandQuery;
import ru.sivak.application.services.IBrandService;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.repositories.BrandRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
public class BrandService implements IBrandService {

    private final BrandRepository brandRepository;
    @Override
    public BrandDto save(Brand brand) {
        brandRepository.save(brand);
        return BrandMapper.toDto(brand);
    }

    @Override
    public void delete(Id id) {
        brandRepository.delete(id);
    }

    @Override
    public List<BrandDto> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(BrandMapper::toDto)
                .toList();
    }

    @Override
    public List<BrandDto> query(BrandQuery query) {
        return brandRepository.query(query)
                .stream()
                .map(BrandMapper::toDto)
                .toList();
    }

    @Override
    public BrandDto get(Id id) {
        return brandRepository.find(id)
                .map(BrandMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
    }
}
