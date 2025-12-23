package com.example.saga.controller;

import com.example.saga.coordinator.SagaCoordinator;
import com.example.saga.model.OrderRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final SagaCoordinator coordinator;

    public OrderController(SagaCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    @PostMapping
    public ResponseEntity<List<String>> createOrder(@RequestBody OrderRequest request) {
        List<String> result = coordinator.startSaga(request.getOrderId(), request.isPaymentSuccess());
        return ResponseEntity.ok(result);
    }
}
