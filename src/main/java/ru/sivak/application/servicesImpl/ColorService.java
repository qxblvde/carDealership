package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.ColorDto;
import ru.sivak.application.mappers.ColorMapper;
import ru.sivak.application.query.ColorQuery;
import ru.sivak.application.services.IColorService;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.repositories.ColorRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ColorService implements IColorService
{
    @NonNull
    private final ColorRepository colorRepository;

    @NonNull
    private final ColorMapper colorMapper;
    @Override
    public ColorDto create(Color color) {
        colorRepository.create(color);
        return colorMapper.map(color);
    }

    
    public ColorDto update(Color color) {
        colorRepository.update(color);
        return colorMapper.map(color);
    }

    @Override
    public void delete(Id id) {
        colorRepository.delete(id);
    }

    @Override
    public List<ColorDto> findAll() {
        return colorRepository.findAll()
                .stream()
                .map(colorMapper::map)
                .toList();
    }

    @Override
    public List<ColorDto> query(ColorQuery query) {
        return colorRepository.query(query)
                .stream()
                .map(colorMapper::map)
                .toList();
    }

    @Override
    public ColorDto get(Id id) {
        return colorRepository.find(id)
                .map(colorMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Color not found"));
    }
}
