package by.javaguru.core.dto.command;

import java.util.UUID;

public class ReserveProductCommand {

    private UUID productId;
    private Integer productQuantity;
    private UUID orderId;

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public ReserveProductCommand(UUID orderId, Integer productQuantity, UUID productId) {
        this.orderId = orderId;
        this.productQuantity = productQuantity;
        this.productId = productId;
    }
}
