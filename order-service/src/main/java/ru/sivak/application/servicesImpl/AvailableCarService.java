package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.AvailableCarDto;
import ru.sivak.application.services.IAvailableCarService;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.grpc.AvailableCarGrpcClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailableCarService implements IAvailableCarService {
    @NonNull
    private final AvailableCarGrpcClient availableCarGrpcClient;

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public List<AvailableCarDto> findAll() {
        return availableCarGrpcClient.findAll();
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public AvailableCarDto get(@NonNull Id id) {
        return availableCarGrpcClient.get(id);
    }
}
