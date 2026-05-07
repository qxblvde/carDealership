package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.DriveDto;
import ru.sivak.application.mappers.DriveMapper;
import ru.sivak.application.query.DriveQuery;
import ru.sivak.application.services.IDriveService;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.repositories.DriveRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
@Service
public class DriveService implements IDriveService {

    @NonNull
    private final DriveRepository driveRepository;

    @NonNull
    private final DriveMapper driveMapper;
    @Override
    public DriveDto create(Drive drive) {
        driveRepository.create(drive);
        return driveMapper.map(drive);
    }

    
    public DriveDto update(Drive drive) {
        driveRepository.update(drive);
        return driveMapper.map(drive);
    }

    @Override
    public void delete(Id id) {
        driveRepository.delete(id);
    }

    @Override
    public List<DriveDto> findAll() {
        return driveRepository.findAll()
                .stream()
                .map(driveMapper::map)
                .toList();
    }

    @Override
    public List<DriveDto> query(DriveQuery query) {
        return driveRepository.query(query)
                .stream()
                .map(driveMapper::map)
                .toList();
    }

    @Override
    public DriveDto get(Id id) {
        return driveRepository.find(id)
                .map(driveMapper::map)
                .orElseThrow(() -> new IllegalArgumentException("Drive not found"));
    }
}
