package com.example.demo.publisher;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publish")
public class DemoPublishController {

    private final DemoRedisMessagePublisher publisher;

    public DemoPublishController(DemoRedisMessagePublisher publisher) {
        this.publisher = publisher;
    }

    @PostMapping
    public ResponseEntity<String> publishMessage(
            @RequestBody DemoMyMessage message) throws Exception {

        publisher.publish(message);
        return ResponseEntity.ok("Message sent to Redis successfully");
    }
}
