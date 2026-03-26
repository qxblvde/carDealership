package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.TransmissonDto;
import ru.sivak.application.mappers.TransmissionMapper;
import ru.sivak.application.query.TransmissionQuery;
import ru.sivak.application.services.ITransmissionService;
import ru.sivak.domain.entities.Transmission;
import ru.sivak.domain.repositories.TransmissonRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
@Service
public class TransmissionService implements ITransmissionService {

    @NonNull
    private final TransmissonRepository transmissionRepository;
    @NonNull
    private final TransmissionMapper transmissionMapper;

    @Override
    public TransmissonDto create(Transmission transmission) {
        transmissionRepository.create(transmission);
        return transmissionMapper.map(transmission);
    }

    
    public TransmissonDto update(Transmission transmission) {
        transmissionRepository.update(transmission);
        return transmissionMapper.map(transmission);
    }

    @Override
    public void delete(Id id) {
        transmissionRepository.delete(id);
    }

    @Override
    public List<TransmissonDto> findAll() {
        return transmissionRepository.findAll()
                .stream()
                .map(transmissionMapper::map)
                .toList();
    }

    @Override
    public List<TransmissonDto> query(TransmissionQuery query) {
        return transmissionRepository.query(query)
                .stream()
                .map(transmissionMapper::map)
                .toList();
    }

    @Override
    public TransmissonDto get(Id id) {
        return transmissionRepository.find(id)
                .map(transmissionMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Transmission not found"));
    }
}
