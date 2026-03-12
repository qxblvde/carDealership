package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.sivak.application.dto.DriveDto;
import ru.sivak.application.mappers.DriveMapper;
import ru.sivak.application.query.DriveQuery;
import ru.sivak.application.services.IDriveService;
import ru.sivak.domain.entities.Drive;
import ru.sivak.domain.repositories.DriveRepository;
import ru.sivak.domain.valueObjects.Id;

import java.util.List;
@RequiredArgsConstructor
public class DriveService implements IDriveService {

    @NonNull
    private final DriveRepository driveRepository;
    @Override
    public DriveDto save(Drive drive) {
        driveRepository.save(drive);
        return DriveMapper.toDriveDto(drive);
    }

    @Override
    public void delete(Id id) {
        driveRepository.delete(id);
    }

    @Override
    public List<DriveDto> findAll() {
        return driveRepository.findAll()
                .stream()
                .map(DriveMapper::toDriveDto)
                .toList();
    }

    @Override
    public List<DriveDto> query(DriveQuery query) {
        return driveRepository.query(query)
                .stream()
                .map(DriveMapper::toDriveDto)
                .toList();
    }

    @Override
    public DriveDto get(Id id) {
        return driveRepository.find(id)
                .map(DriveMapper::toDriveDto)
                .orElseThrow(() -> new IllegalArgumentException("Drive not found"));
    }
}
