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

    public UUID getProductId() {
        return productId;
    }

    public UUID getOrderID() {
        return orderId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setOrderId(UUID orderId) {
        // пока не реализовано
    }

    @Override
    public String toString() {
        return "OrderCreatedEvent{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", productId=" + productId +
                ", productQuantity=" + productQuantity +
                '}';
    }

}
