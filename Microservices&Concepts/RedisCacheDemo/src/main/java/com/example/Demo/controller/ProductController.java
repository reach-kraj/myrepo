package com.example.Demo.controller;

import com.example.Demo.model.Product;
import com.example.Demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable int id) {
        long start = System.currentTimeMillis();
        Product product = productService.getProductById(id);
        long end = System.currentTimeMillis();
        System.out.println("Time taken: " + (end - start) + "ms");
        return product;
    }
}

