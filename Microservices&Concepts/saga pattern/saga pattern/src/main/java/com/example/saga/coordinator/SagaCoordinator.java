package com.example.saga.coordinator;

import com.example.saga.Service.InventoryService;
import com.example.saga.Service.OrderService;
import com.example.saga.Service.PaymentService;
import com.example.saga.event.EventBus;
import org.springframework.stereotype.Component;
import com.example.saga.log.SagaLog;

import java.util.List;

@Component
public class SagaCoordinator {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final InventoryService inventoryService;
    private final SagaLog sagaLog;

    public SagaCoordinator(OrderService orderService, PaymentService paymentService,
                           InventoryService inventoryService, SagaLog sagaLog) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.inventoryService = inventoryService;
        this.sagaLog = sagaLog;
    }

    public List<String> startSaga(String orderId, boolean paymentSuccess) {
        EventBus.clear();
        sagaLog.clear();

        EventBus.subscribe("OrderCreated", event -> paymentService.process(orderId, paymentSuccess));
        EventBus.subscribe("PaymentCompleted", event -> inventoryService.process(orderId));
        EventBus.subscribe("PaymentFailed", event -> orderService.cancelOrder(orderId));
        EventBus.subscribe("InventoryFailed", event -> orderService.cancelOrder(orderId));

        orderService.createOrder(orderId);

        return sagaLog.getMessages();
    }
}
