package com.example.demo.controller;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
  @Value("${server.port}")
  private String port;

  @GetMapping("/product")
  public String getProduct() {
    return "Product data served from port: " + port;
  }
}
