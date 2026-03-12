package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
public class InStockOrderService implements IInStockOrderService {
    @NonNull
    private final InStockOrderRepository inStockOrderRepository;
    @NonNull
    private final CarRepository carRepository;

    public OrderDto create(@NonNull Id clientId, @NonNull Id carId) {
        Car car = carRepository.find(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        InStockOrder order = new InStockOrder(Id.newId(), Id.newId(), clientId, car);
        inStockOrderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    public List<OrderDto> query(@NonNull InStockOrderQuery query) {
        return inStockOrderRepository.query(query)
                .stream()
                .map(OrderMapper::toDto)
                .toList();
    }

    public void approve(@NonNull Id orderId) {
        get(orderId).approve();
    }
    public void requestPayment(@NonNull Id orderId) {
        get(orderId).requestPayment();
    }
    public void pay(@NonNull Id orderId) {
        get(orderId).pay();
    }

    public void complete(@NonNull Id orderId) {
        get(orderId).complete();
    }
    public void cancel(@NonNull Id orderId) {
        get(orderId).cancel();
    }
    public void markAsReady(@NonNull Id orderId) {
        get(orderId).markAsReady();
    }

    public InStockOrder get(@NonNull Id id) {
        return inStockOrderRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
    }

    public OrderDto update(@NonNull Id orderId, @NonNull Id newClientId, @NonNull Id newCarId) {
        InStockOrder order = get(orderId);

        Car newCar = carRepository.find(newCarId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        order.updateClient(newClientId);
        order.updateCar(newCar);

        inStockOrderRepository.save(order);

        return OrderMapper.toDto(order);
    }
}

