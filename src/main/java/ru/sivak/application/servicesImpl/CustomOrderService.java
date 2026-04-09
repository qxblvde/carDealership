package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

    public OrderDto create(@NonNull Id managerId, @NonNull Id clientId, @NonNull Id carId) {
        Car car = carRepository.find(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        CustomOrder order = new CustomOrder(Id.newId(), managerId, clientId, car);
        customOrderRepository.create(order);
        return orderMapper.map(order);
    }

    public List<OrderDto> query(@NonNull CustomOrderQuery query) {
        return customOrderRepository.query(query)
                .stream()
                .map(orderMapper::map)
                .toList();

    }

    public void approve(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.approve()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }
    public void requestPayment(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.requestPayment()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }
    public void pay(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.pay()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }
    public void requestDelivery(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.requestDelivery()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }
    public void complete(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.complete()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }
    public void cancel(@NonNull Id orderId) {
        CustomOrder order = get(orderId);
        if (!order.cancel()) {
            throw new IllegalArgumentException("Invalid custom order transition");
        }
        customOrderRepository.update(order);
    }
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

    public OrderDto getDto(@NonNull Id id) {
        return orderMapper.map(get(id));
    }

    public OrderDto update(@NonNull Id orderId, @NonNull Id newClientId, @NonNull Id newCarId) {
        CustomOrder order = get(orderId);

        Car newCar = carRepository.find(newCarId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        order.updateClient(newClientId);
        order.updateCar(newCar);

        customOrderRepository.update(order);

        return orderMapper.map(order);
    }

}
