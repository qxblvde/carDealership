package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.BrandDto;
import ru.sivak.application.mappers.BrandMapper;
import ru.sivak.application.query.BrandQuery;
import ru.sivak.application.services.IBrandService;
import ru.sivak.domain.entities.Brand;
import ru.sivak.domain.repositories.BrandRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BrandService implements IBrandService {
    @NonNull
    private final BrandRepository brandRepository;

    @NonNull
    private final BrandMapper brandMapper;
    @Override
    public BrandDto create(Brand brand) {
        brandRepository.create(brand);
        return brandMapper.map(brand);
    }

    
    public BrandDto update(Brand brand) {
        brandRepository.update(brand);
        return brandMapper.map(brand);
    }

    @Override
    public void delete(Id id) {
        brandRepository.delete(id);
    }

    @Override
    public List<BrandDto> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::map)
                .toList();
    }

    @Override
    public List<BrandDto> query(BrandQuery query) {
        return brandRepository.query(query)
                .stream()
                .map(brandMapper::map)
                .toList();
    }

    @Override
    public BrandDto get(Id id) {
        return brandRepository.find(id)
                .map(brandMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Brand not found"));
    }
}
