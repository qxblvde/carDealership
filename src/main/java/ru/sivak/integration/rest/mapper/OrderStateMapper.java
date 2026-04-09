package ru.sivak.integration.rest.mapper;

import ru.sivak.domain.order.custom.*;
import ru.sivak.domain.order.inStock.InStockOrderState;

public final class OrderStateMapper {
    private OrderStateMapper() {
    }

    public static Class<? extends CustomOrderState> parseCustomState(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return switch (value) {
            case "CREATED" -> CreatedState.class;
            case "APPROVED" -> ApprovedState.class;
            case "WAITING_PAYMENT" -> WaitingPaymentState.class;
            case "PAID" -> PaidState.class;
            case "WAITING_DELIVERY" -> WaitingDeliveryState.class;
            case "READY_FOR_PICK_UP" -> ReadyForPickUpState.class;
            case "COMPLETED" -> CompletedState.class;
            case "CANCELED" -> CanceledState.class;
            default -> throw new IllegalArgumentException("Unsupported custom order state: " + value);
        };
    }

    public static Class<? extends InStockOrderState> parseInStockState(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return switch (value) {
            case "CREATED" -> ru.sivak.domain.order.inStock.CreatedState.class;
            case "APPROVED" -> ru.sivak.domain.order.inStock.ApprovedState.class;
            case "WAITING_PAYMENT" -> ru.sivak.domain.order.inStock.WaitingPaymentState.class;
            case "PAID" -> ru.sivak.domain.order.inStock.PaidState.class;
            case "READY_FOR_PICK_UP" -> ru.sivak.domain.order.inStock.ReadyForPickUpState.class;
            case "COMPLETED" -> ru.sivak.domain.order.inStock.CompletedState.class;
            case "CANCELED" -> ru.sivak.domain.order.inStock.CanceledState.class;
            default -> throw new IllegalArgumentException("Unsupported in-stock order state: " + value);
        };
    }
}

