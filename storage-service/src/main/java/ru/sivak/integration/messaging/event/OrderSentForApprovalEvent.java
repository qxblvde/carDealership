package ru.sivak.integration.messaging.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderSentForApprovalEvent {
    private UUID orderId;
    private String orderType;
    private UUID carId;
    private String traceId;
}
