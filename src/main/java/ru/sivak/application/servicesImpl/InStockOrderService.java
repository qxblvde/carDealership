package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.sivak.application.dto.OrderDto;
import ru.sivak.application.mappers.OrderMapper;
import ru.sivak.application.query.InStockOrderQuery;
import ru.sivak.application.services.IInStockOrderService;
import ru.sivak.domain.entities.Car;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.repositories.CarRepository;
import ru.sivak.domain.repositories.InStockOrderRepository;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.infrastructure.security.AuthenticatedUserService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InStockOrderService implements IInStockOrderService {
    @NonNull
    private final InStockOrderRepository inStockOrderRepository;
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
        InStockOrder order = new InStockOrder(Id.newId(), managerId, clientId, car);

        inStockOrderRepository.create(order);
        return orderMapper.map(order);
    }

    @PreAuthorize("hasRole('USER') or hasRole('MANAGER') or hasRole('ADMIN')")
    public List<OrderDto> query(@NonNull InStockOrderQuery query) {
        InStockOrderQuery dublicate = query;
        if (authenticatedUserService.hasRole("MANAGER") == false && authenticatedUserService.hasRole("ADMIN") == false) {
            dublicate = query.toBuilder()
                    .clientId(authenticatedUserService.getCurrentUserId())
                    .build();
        }

        return inStockOrderRepository.query(dublicate)
                .stream()
                .map(orderMapper::map)
                .toList();
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void approve(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.approve()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void requestPayment(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.requestPayment()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentInStockOrderOwner(#orderId))")
    public void pay(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.pay()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('MANAGER') or hasRole('ADMIN')")
    public void complete(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.complete()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentInStockOrderOwner(#orderId))")
    public void cancel(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.cancel()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }

    @PreAuthorize("hasRole('WAREHOUSE_ADMIN') or hasRole('ADMIN')")
    public void markAsReady(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.markAsReady()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }

    public InStockOrder get(@NonNull Id id) {
        return inStockOrderRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or (hasRole('USER') and @orderAccessChecker.isCurrentInStockOrderOwner(#id))")
    public OrderDto getDto(@NonNull Id id) {
        return orderMapper.map(get(id));
    }

    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @orderAccessChecker.isCurrentInStockOrderOwner(#orderId))")
    public OrderDto update(@NonNull Id orderId, @NonNull Id newCarId) {
        InStockOrder order = get(orderId);

        Car newCar = carRepository.find(newCarId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        order.updateCar(newCar);
        inStockOrderRepository.update(order);

        return orderMapper.map(order);
    }
}
