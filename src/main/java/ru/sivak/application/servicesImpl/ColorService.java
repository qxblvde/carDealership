package ru.sivak.application.servicesImpl;

import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.ColorDto;
import ru.sivak.application.mappers.ColorMapper;
import ru.sivak.application.query.ColorQuery;
import ru.sivak.application.services.IColorService;
import ru.sivak.domain.entities.Color;
import ru.sivak.domain.repositories.ColorRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;

@RequiredArgsConstructor
public class ColorService implements IColorService
{

    private final ColorRepository colorRepository;
    @Override
    public ColorDto save(Color color) {
        colorRepository.save(color);
        return ColorMapper.toDto(color);
    }

    @Override
    public void delete(Id id) {
        colorRepository.delete(id);
    }

    @Override
    public List<ColorDto> findAll() {
        return colorRepository.findAll()
                .stream()
                .map(ColorMapper::toDto)
                .toList();
    }

    @Override
    public List<ColorDto> query(ColorQuery query) {
        return colorRepository.query(query)
                .stream()
                .map(ColorMapper::toDto)
                .toList();
    }

    @Override
    public ColorDto get(Id id) {
        return colorRepository.find(id)
                .map(ColorMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Color not found"));
    }
}
