package com.example.saga.Service;

import com.example.saga.event.EventBus;
import com.example.saga.log.SagaLog;
import org.springframework.stereotype.Component;

@Component
public class OrderService {
    private final SagaLog sagaLog;

    public OrderService(SagaLog sagaLog) {
        this.sagaLog = sagaLog;
    }

    public void createOrder(String orderId) {
        sagaLog.log("✅ Order Created: " + orderId);
        EventBus.publish("OrderCreated", orderId);
    }

    public void cancelOrder(String orderId) {
        sagaLog.log("❌ Order Cancelled: " + orderId);
    }
}
