package by.javaguru.orders.saga;


import by.javaguru.core.dto.command.ProcessPaymentCommand;
import by.javaguru.core.dto.command.ReserveProductCommand;
import by.javaguru.core.dto.event.OrderCreatedEvent;
import by.javaguru.core.dto.event.ProductReservedEvent;
import by.javaguru.core.dto.event.ProductReservationFailedEvent;
import by.javaguru.core.types.OrderStatus;
import by.javaguru.orders.service.OrderHistoryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
  /* listen that topic */
@KafkaListener(topics = { "${orders.events.topic.name}",
                        "${products.events.topic.name}",
}
)
public class OrderSaga {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String productsCommandsTopicName;
    private final OrderHistoryService orderHistoryService;
    private final String paymentsCommandsTopicName;

    public OrderSaga(KafkaTemplate<String, Object> kafkaTemplate,
                     @Value("${products.commands.topic.name}")
                     String productsCommandsTopicName,
                     @Value("${payments.commands.topic.name}")
                     String paymentsCommandsTopicName,
                     OrderHistoryService orderHistoryService
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.productsCommandsTopicName = productsCommandsTopicName;
        this.orderHistoryService = orderHistoryService;
        this.paymentsCommandsTopicName = paymentsCommandsTopicName;
    }

    @KafkaHandler
    /* listen for offset with type "OrderCreatedEvent" */
    public void handleEvent(@Payload OrderCreatedEvent event) {

        System.out.println(" --------------------------------- " + "/n");
        System.out.println(" OrderCreatedEvent== " );
        System.out.println(" --------------------------------- " + "/n");

        ReserveProductCommand command = new ReserveProductCommand(
                event.getProductId(),
                event.getProductQuantity(),
                event.getOrderId()
        );
        /* send msg in Kafka-topic and return completable future */
        kafkaTemplate.send(productsCommandsTopicName, command);
        orderHistoryService.add(event.getOrderId(), OrderStatus.CREATED);
    }

    @KafkaHandler
    /* listen for offset with type POSITIVE "ProductReservedEvent" */
    public void handleEvent(@Payload ProductReservedEvent event) {

        System.out.println(" --------------------------------- " + "/n");
        System.out.println(" ProductReservedEvent== " );
        System.out.println(" --------------------------------- " + "/n");

         ProcessPaymentCommand processPaymentCommand = new ProcessPaymentCommand(
                event.getOrderId(),
                event.getProductId(),
                event.getProductPriceId(),
                event.getProductQuantity()
        );
        /* send msg in Kafka-topic and return completable future */
        kafkaTemplate.send(paymentsCommandsTopicName, processPaymentCommand);
    }

    @KafkaHandler
    /* listen for offset with type "ProductReservationFailedEvent" */
    public void handleEvent(@Payload ProductReservationFailedEvent event) {

        System.out.println("  ---------------------------------  " + "\n");
        System.out.println(" ProductReservationFailedEvent== handler ");
        System.out.println("  ---------------------------------  " + "\n");

    }


}
