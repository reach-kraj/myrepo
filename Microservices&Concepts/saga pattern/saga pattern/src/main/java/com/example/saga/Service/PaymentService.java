package com.example.saga.Service;
import com.example.saga.event.EventBus;
import com.example.saga.log.SagaLog;
import org.springframework.stereotype.Component;

@Component
public class PaymentService {
    private final SagaLog sagaLog;

    public PaymentService(SagaLog sagaLog) {
        this.sagaLog = sagaLog;
    }

    public void process(String orderId, boolean paymentSuccess) {
        sagaLog.log("💳 Processing payment for " + orderId);

        if (paymentSuccess) {
            sagaLog.log("✅ Payment Successful for " + orderId);
            EventBus.publish("PaymentCompleted", orderId);
        } else {
            sagaLog.log("❌ Payment Failed for " + orderId);
            EventBus.publish("PaymentFailed", orderId);
        }
    }
}
