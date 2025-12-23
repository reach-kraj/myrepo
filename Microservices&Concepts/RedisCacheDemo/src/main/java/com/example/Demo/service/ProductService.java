package com.example.Demo.service;

import com.example.Demo.model.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Cacheable(value = "products", key = "#id")
    public Product getProductById(int id) {
        System.out.println("Fetching from DB (with delay)...");
        try {
            Thread.sleep(6000); // simulate 6-second DB call
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new Product(id, "Sample Product " + id, 100.0);
    }
}
