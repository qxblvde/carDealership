package ru.sivak.infrastructure.repositories.jpa;

import ru.sivak.domain.entities.TestDriveRequest;
import ru.sivak.domain.order.custom.CustomOrder;
import ru.sivak.domain.order.custom.CustomOrderState;
import ru.sivak.domain.order.BaseOrder;
import ru.sivak.domain.order.inStock.InStockOrder;
import ru.sivak.domain.order.inStock.InStockOrderState;
import ru.sivak.domain.valueObjects.Id;
import ru.sivak.domain.valueObjects.Money;
import ru.sivak.infrastructure.persistence.entity.*;

public final class JpaDomainMapper {
    private JpaDomainMapper() {
    }

    public static CustomOrder toDomain(CustomOrderEntity entity) {
        CustomOrder order = new CustomOrder(
                Id.of(entity.getId()),
                Id.of(entity.getManager().getId()),
                Id.of(entity.getClient().getId()),
                Id.of(entity.getCarId()),
                Money.of(entity.getPrice())
        );
        applyCustomState(order, entity.getState());
        return order;
    }

    public static InStockOrder toDomain(InStockOrderEntity entity) {
        InStockOrder order = new InStockOrder(
                Id.of(entity.getId()),
                Id.of(entity.getManager().getId()),
                Id.of(entity.getClient().getId()),
                Id.of(entity.getCarId()),
                Money.of(entity.getPrice())
        );
        applyInStockState(order, entity.getState());
        return order;
    }

    public static TestDriveRequest toDomain(TestDriveRequestEntity entity) {
        return TestDriveRequest.builder()
                .id(Id.of(entity.getId()))
                .clientId(Id.of(entity.getClient().getId()))
                .carId(Id.of(entity.getCarId()))
                .scheduledTime(entity.getScheduledTime())
                .build();
    }

    public static void mapToEntity(
            CustomOrder source,
            CustomOrderEntity target,
            AppUserEntity manager,
            AppUserEntity client
    ) {
        mapOrder(source, target, manager, client, customStateCode(source.getStateType()));
        target.setCarId(source.getCarId().getId());
    }

    public static void mapToEntity(
            InStockOrder source,
            InStockOrderEntity target,
            AppUserEntity manager,
            AppUserEntity client
    ) {
        mapOrder(source, target, manager, client, inStockStateCode(source.getStateType()));
        target.setCarId(source.getCarId().getId());
    }

    public static void mapToEntity(
            TestDriveRequest source,
            TestDriveRequestEntity target,
            AppUserEntity client
    ) {
        target.setId(source.getId().getId());
        target.setClient(client);
        target.setCarId(source.getCarId().getId());
        target.setScheduledTime(source.getScheduledTime());
    }

    public static String customStateCode(Class<? extends CustomOrderState> type) {
        if (type == ru.sivak.domain.order.custom.CreatedState.class) return "CREATED";
        if (type == ru.sivak.domain.order.custom.ApprovedState.class) return "APPROVED";
        if (type == ru.sivak.domain.order.custom.WaitingPaymentState.class) return "WAITING_PAYMENT";
        if (type == ru.sivak.domain.order.custom.PaidState.class) return "PAID";
        if (type == ru.sivak.domain.order.custom.WaitingDeliveryState.class) return "WAITING_DELIVERY";
        if (type == ru.sivak.domain.order.custom.ReadyForPickUpState.class) return "READY_FOR_PICK_UP";
        if (type == ru.sivak.domain.order.custom.CompletedState.class) return "COMPLETED";
        if (type == ru.sivak.domain.order.custom.CanceledState.class) return "CANCELED";
        throw new IllegalArgumentException("Unsupported custom order state: " + type.getName());
    }

    public static String inStockStateCode(Class<? extends InStockOrderState> type) {
        if (type == ru.sivak.domain.order.inStock.CreatedState.class) return "CREATED";
        if (type == ru.sivak.domain.order.inStock.ApprovedState.class) return "APPROVED";
        if (type == ru.sivak.domain.order.inStock.WaitingPaymentState.class) return "WAITING_PAYMENT";
        if (type == ru.sivak.domain.order.inStock.PaidState.class) return "PAID";
        if (type == ru.sivak.domain.order.inStock.ReadyForPickUpState.class) return "READY_FOR_PICK_UP";
        if (type == ru.sivak.domain.order.inStock.CompletedState.class) return "COMPLETED";
        if (type == ru.sivak.domain.order.inStock.CanceledState.class) return "CANCELED";
        throw new IllegalArgumentException("Unsupported in-stock order state: " + type.getName());
    }

    private static void mapOrder(
            BaseOrder source,
            AbstractOrderEntity target,
            AppUserEntity manager,
            AppUserEntity client,
            String stateCode
    ) {
        target.setId(source.getId().getId());
        target.setManager(manager);
        target.setClient(client);
        target.setState(stateCode);
        target.setPrice(source.getPrice().getAmount());
    }

    private static void applyCustomState(CustomOrder order, String stateCode) {
        switch (stateCode) {
            case "CREATED": return;
            case "APPROVED": order.approve(); return;
            case "WAITING_PAYMENT": order.approve(); order.requestPayment(); return;
            case "PAID": order.approve(); order.requestPayment(); order.pay(); return;
            case "WAITING_DELIVERY": order.approve(); order.requestPayment(); order.pay(); order.requestDelivery(); return;
            case "READY_FOR_PICK_UP": order.approve(); order.requestPayment(); order.pay(); order.requestDelivery(); order.markAsReady(); return;
            case "COMPLETED": order.approve(); order.requestPayment(); order.pay(); order.requestDelivery(); order.markAsReady(); order.complete(); return;
            case "CANCELED": order.cancel(); return;
            default: throw new IllegalArgumentException("Unsupported custom order state code: " + stateCode);
        }
    }

    private static void applyInStockState(InStockOrder order, String stateCode) {
        switch (stateCode) {
            case "CREATED": return;
            case "APPROVED": order.approve(); return;
            case "WAITING_PAYMENT": order.approve(); order.requestPayment(); return;
            case "PAID": order.approve(); order.requestPayment(); order.pay(); return;
            case "READY_FOR_PICK_UP": order.approve(); order.requestPayment(); order.pay(); order.markAsReady(); return;
            case "COMPLETED": order.approve(); order.requestPayment(); order.pay(); order.markAsReady(); order.complete(); return;
            case "CANCELED": order.cancel(); return;
            default: throw new IllegalArgumentException("Unsupported in-stock order state code: " + stateCode);
        }
    }
}
