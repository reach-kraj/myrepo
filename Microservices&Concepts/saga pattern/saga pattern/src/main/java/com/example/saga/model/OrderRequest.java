package com.example.saga.model;

import lombok.Data;

@Data
public class OrderRequest {
    private String orderId;
    private boolean paymentSuccess;
}

