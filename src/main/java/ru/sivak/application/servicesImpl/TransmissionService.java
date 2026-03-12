package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.TransmissonDto;
import ru.sivak.application.mappers.TransmissionMapper;
import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.application.services.ITransmissionService;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.repositories.TransmissonRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
public class TransmissionService implements ITransmissionService {

    @NonNull
    private final TransmissonRepository transmissionRepository;

    @Override
    public TransmissonDto save(Transmission transmission) {
        transmissionRepository.save(transmission);
        return TransmissionMapper.toDto(transmission);
    }

    @Override
    public void delete(Id id) {
        transmissionRepository.delete(id);
    }

    @Override
    public List<TransmissonDto> findAll() {
        return transmissionRepository.findAll()
                .stream()
                .map(TransmissionMapper::toDto)
                .toList();
    }

    @Override
    public List<TransmissonDto> query(TransmissionQuery query) {
        return transmissionRepository.query(query)
                .stream()
                .map(TransmissionMapper::toDto)
                .toList();
    }

    @Override
    public TransmissonDto get(Id id) {
        return transmissionRepository.find(id)
                .map(TransmissionMapper::toDto)
                .orElseThrow(() -> new IllegalArgumentException("Transmission not found"));
    }
}
