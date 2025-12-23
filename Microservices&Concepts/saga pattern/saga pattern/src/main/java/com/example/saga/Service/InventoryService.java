package com.example.saga.Service;

import com.example.saga.event.EventBus;

import com.example.saga.log.SagaLog;
import org.springframework.stereotype.Component;

@Component
public class InventoryService {
    private final SagaLog sagaLog;

    public InventoryService(SagaLog sagaLog) {
        this.sagaLog = sagaLog;
    }

    public void process(String orderId) {
        sagaLog.log("📦 Reserving inventory for " + orderId);

        boolean inventoryAvailable = true; // Simulated check

        if (inventoryAvailable) {
            sagaLog.log("✅ Inventory Reserved. Order Fulfilled: " + orderId);
        } else {
            sagaLog.log("❌ Inventory Unavailable for " + orderId);
            EventBus.publish("InventoryFailed", orderId);
        }
    }
}
