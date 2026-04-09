package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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

    public OrderDto create(@NonNull Id managerId, @NonNull Id clientId, @NonNull Id carId) {
        Car car = carRepository.find(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        InStockOrder order = new InStockOrder(Id.newId(), managerId, clientId, car);
        inStockOrderRepository.create(order);
        return orderMapper.map(order);
    }

    public List<OrderDto> query(@NonNull InStockOrderQuery query) {
        return inStockOrderRepository.query(query)
                .stream()
                .map(orderMapper::map)
                .toList();
    }

    public void approve(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.approve()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }
    public void requestPayment(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.requestPayment()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }
    public void pay(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.pay()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }

    public void complete(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.complete()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }
    public void cancel(@NonNull Id orderId) {
        InStockOrder order = get(orderId);
        if (!order.cancel()) {
            throw new IllegalArgumentException("Invalid in-stock order transition");
        }
        inStockOrderRepository.update(order);
    }
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

    public OrderDto getDto(@NonNull Id id) {
        return orderMapper.map(get(id));
    }

    public OrderDto update(@NonNull Id orderId, @NonNull Id newClientId, @NonNull Id newCarId) {
        InStockOrder order = get(orderId);

        Car newCar = carRepository.find(newCarId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        order.updateClient(newClientId);
        order.updateCar(newCar);

        inStockOrderRepository.update(order);

        return orderMapper.map(order);
    }

}
