package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.mappers.OrderMapper;
import ru.sivak.application.query.CustomOrderQuery;
import ru.sivak.application.services.ICustomOrderService;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.repositories.CustomOrderRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.security.AuthenticatedUserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CustomOrderService implements ICustomOrderService {
    @NonNull
    private final CustomOrderRepository customOrderRepository;
    @NonNull
    private final CarRepository carRepository;
    @NonNull
    private final OrderMapper orderMapper;
    @NonNull
    private final AuthenticatedUserService authenticatedUserService;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public OrderDto create(@NonNull Id managerId, @NonNull Id carId) {
        Car car = carRepository.find(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        Id clientId = authenticatedUserService.getCurrentUserId();
        CustomOrder order = new CustomOrder(Id.newId(), managerId, clientId, car);

        customOrderRepository.create(order);
        return orderMapper.map(order);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public List<OrderDto> query(@NonNull CustomOrderQuery query) {
        CustomOrderQuery dublicate = query;
        if (authenticatedUserService.hasRole("MANAGER") == false && authenticatedUserService.hasRole("ADMIN") == false) {
            dublicate = query.toBuilder()
                    .clientId(authenticatedUserService.getCurrentUserId())
                    .build();
        }

        return customOrderRepository.query(dublicate)
                .stream()
                .map(orderMapper::map)
                .toList();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void approve(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.approve()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void requestPayment(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.requestPayment()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentCustomOrderOwner(#orderId))")
    public void pay(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.pay()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public void requestDelivery(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.requestDelivery()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void complete(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.complete()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentCustomOrderOwner(#orderId))")
    public void cancel(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.cancel()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public void markAsReady(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.markAsReady()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }

    public CustomOrder get(@NonNull Id id) {
        return customOrderRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or (hasRole('USER') and @orderAccessChecker.isCurrentCustomOrderOwner(#id))")
    public OrderDto getDto(@NonNull Id id) {
        return orderMapper.map(get(id));
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentCustomOrderOwner(#orderId))")
    public OrderDto update(@NonNull Id orderId, @NonNull Id newCarId) {
        CustomOrder order = get(orderId);

        Car newCar = carRepository.find(newCarId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        order.updateCar(newCar);
        customOrderRepository.update(order);

        return orderMapper.map(order);
    }
}
