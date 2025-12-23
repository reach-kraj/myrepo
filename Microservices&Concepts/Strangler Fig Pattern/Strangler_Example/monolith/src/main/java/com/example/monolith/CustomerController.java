package com.example.monolith;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @GetMapping("/{id}")
    public String getCustomer(@PathVariable String id) {
        return "Customer from Monolith: ID = " + id;
    }
}