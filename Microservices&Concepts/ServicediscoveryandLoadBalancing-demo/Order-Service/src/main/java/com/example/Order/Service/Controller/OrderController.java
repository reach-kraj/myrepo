package com.example.Order.Service.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

  @Autowired
  private RestTemplate restTemplate;

  @GetMapping("/order")
  public String placeOrder() {
    String product = restTemplate.getForObject("http://demo-service/product", String.class);
    return "Order placed for -> " + product;
  }
}
