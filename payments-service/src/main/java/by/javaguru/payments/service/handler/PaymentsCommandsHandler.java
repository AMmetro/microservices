package by.javaguru.payments.service.handler;


import by.javaguru.core.dto.Payment;
import by.javaguru.core.dto.Product;
import by.javaguru.core.dto.command.ProcessPaymentCommand;
import by.javaguru.core.dto.command.ReserveProductCommand;
import by.javaguru.core.dto.event.ProductReservationFailedEvent;
import by.javaguru.core.dto.event.ProductReservedEvent;
import by.javaguru.payments.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics="${payments.commands.topic.name}")
public class PaymentsCommandsHandler {

    private final PaymentService paymentService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String paymentsEventsTopicName;

    public PaymentsCommandsHandler(PaymentService paymentService,
                                   KafkaTemplate<String, Object> kafkaTemplate,
                                   @Value("${payments.events.topic.name}")
                                   String paymentsEventsTopicName) {
        this.paymentService = paymentService;
        this.kafkaTemplate = kafkaTemplate;
        this.paymentsEventsTopicName = paymentsEventsTopicName;
    }


    @KafkaHandler
    public void handleCommand(@Payload ProcessPaymentCommand command) {

        System.out.println(" ------------------------------------------  " + "\n");
        System.out.println("PaymentsCommandsHandler==  ");

        Payment payment = new Payment(command.getOrderId(),
                command.getProductId(),
                command.getProductPrice(),
                command.getProductQuantity());

        Payment processedPayment = paymentService.process(payment);
    }

}
