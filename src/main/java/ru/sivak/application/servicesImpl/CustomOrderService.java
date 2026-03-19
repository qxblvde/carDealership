package ru.sivak.application.servicesImpl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
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
public class CustomOrderService implements ICustomOrderService {
    @NonNull
    private final CustomOrderRepository customOrderRepository;
    @NonNull
    private final CarRepository carRepository;

    public OrderDto create(@NonNull Id clientId, @NonNull Id carId) {
        Car car = carRepository.find(carId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));
        CustomOrder order = new CustomOrder(Id.newId(), Id.newId(), clientId, car);
        customOrderRepository.save(order);
        return OrderMapper.toDto(order);
    }

    public List<OrderDto> query(@NonNull CustomOrderQuery query) {
        return customOrderRepository.query(query)
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
    public void requestDelivery(@NonNull Id orderId) {
        get(orderId).requestDelivery();
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

    public CustomOrder get(@NonNull Id id) {
        return customOrderRepository.find(id)
                .orElseThrow(() -> new IllegalArgumentException("order not found"));
    }

    public OrderDto update(@NonNull Id orderId, @NonNull Id newClientId, @NonNull Id newCarId) {
        CustomOrder order = get(orderId);

        Car newCar = carRepository.find(newCarId)
                .orElseThrow(() -> new IllegalArgumentException("Car not found"));

        order.updateClient(newClientId);
        order.updateCar(newCar);

        customOrderRepository.save(order);

        return OrderMapper.toDto(order);
    }
}
