package co.edu.unicauca.degreework.Comunication;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@EnableRabbit
public class Publisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue notificationQueue;
    public Publisher(RabbitTemplate rabbitTemplate, Queue notificationQueue) {
        this.rabbitTemplate = rabbitTemplate;
        this.notificationQueue = notificationQueue;
    }
    public void sendMessageNotificationQueue(final String message) {
        System.out.println("=== Sending message to queue ===");
        rabbitTemplate.convertAndSend(notificationQueue.getName(), message);
    }
}
