package com.example.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            // Declare the queue
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            // Send multiple messages
            int numberOfMessages = 10;
            for (int i = 1; i <= numberOfMessages; i++) {
                String message = "Hello World! Message #" + i;
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
        }
    }
}