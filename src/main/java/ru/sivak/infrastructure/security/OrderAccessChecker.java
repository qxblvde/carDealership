package ru.sivak.infrastructure.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sivak.domain.repositories.CustomOrderRepository;
import ru.sivak.domain.repositories.InStockOrderRepository;
import ru.sivak.domain.repositories.TestDriveRequestRepository;
import ru.sivak.domain.valueObjects.Id;

@Component
@RequiredArgsConstructor
public class OrderAccessChecker {
    private final AuthenticatedUserService authenticatedUserService;
    private final CustomOrderRepository customOrderRepository;
    private final InStockOrderRepository inStockOrderRepository;
    private final TestDriveRequestRepository testDriveRequestRepository;

    public boolean isCurrentCustomOrderOwner(Id orderId) {
        if (orderId == null) {
            return false;
        }

        Id currentUserId = authenticatedUserService.getCurrentUserId();
        return customOrderRepository.find(orderId)
                .map(order -> order.getClientId().equals(currentUserId))
                .orElse(false);
    }

    public boolean isCurrentInStockOrderOwner(Id orderId) {
        if (orderId == null) {
            return false;
        }

        Id currentUserId = authenticatedUserService.getCurrentUserId();
        return inStockOrderRepository.find(orderId)
                .map(order -> order.getClientId().equals(currentUserId))
                .orElse(false);
    }

    public boolean isCurrentTestDriveOwner(Id requestId) {
        if (requestId == null) {
            return false;
        }

        Id currentUserId = authenticatedUserService.getCurrentUserId();
        return testDriveRequestRepository.find(requestId)
                .map(request -> request.getClientId().equals(currentUserId))
                .orElse(false);
    }
}
