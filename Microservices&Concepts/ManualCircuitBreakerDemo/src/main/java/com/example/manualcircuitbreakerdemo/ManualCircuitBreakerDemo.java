package com.example.manualcircuitbreakerdemo;

public class ManualCircuitBreakerDemo {
    static class PaymentService {
        private int attemptCount = 0;

        public String processPayment(String paymentId) {
            attemptCount++;
            // Fixed sequence: succeed, fail, fail, succeed, succeed, ...
            if (attemptCount == 2 || attemptCount == 3) {
                throw new RuntimeException("Payment service failed!");
            }
            return "Payment " + paymentId + " processed successfully!";
        }
    }

    public static void main(String[] args) {
        // Create the Circuit Breaker (2 seconds in OPEN)
        CircuitBreaker circuitBreaker = new CircuitBreaker(2000);
        PaymentService paymentService = new PaymentService();

        // Simulate 8 payment attempts
        for (int i = 1; i <= 8; i++) {
            String paymentId = "PAY" + i;
            System.out.println("Attempt " + i + " for " + paymentId + ":");

            String result = circuitBreaker.execute(
                    () -> paymentService.processPayment(paymentId),
                    "Fallback: Payment service is unavailable. Please try again later."
            );

            System.out.println("Result: " + result);
            System.out.println("Circuit Breaker State: " + circuitBreaker.getState());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("---");
        }
    }
}