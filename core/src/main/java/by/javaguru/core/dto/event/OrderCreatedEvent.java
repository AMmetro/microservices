package by.javaguru.core.dto.event;


import java.util.UUID;

public class OrderCreatedEvent {
    private UUID orderId;
    private UUID customerId;
    private UUID productId;
    private Integer productQuantity;

    public OrderCreatedEvent(UUID orderId, UUID customerId, UUID productId, Integer productQuantity) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public UUID getOrderID() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        // пока не реализовано
    }
}
