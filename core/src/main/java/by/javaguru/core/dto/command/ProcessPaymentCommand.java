package by.javaguru.core.dto.command;

import java.math.BigDecimal;
import java.util.UUID;

public class ProcessPaymentCommand {
    private UUID orderId;
    private UUID productId;
    private BigDecimal productPrice;
    private Integer productQuantity;

    public ProcessPaymentCommand() {
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

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

    public ProcessPaymentCommand(UUID orderId, UUID productId, BigDecimal productPrice, Integer productQuantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
    }
}
