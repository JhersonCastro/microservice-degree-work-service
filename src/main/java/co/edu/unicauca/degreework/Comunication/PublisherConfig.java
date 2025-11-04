package co.edu.unicauca.degreework.Comunication;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PublisherConfig {

    @Value("${notificationQueue.name}")
    private String notificationQueue;

    /**
     * Creates notification queue bean
     * @return Configured queue
     */
    @Bean
    public Queue notificationQueue() {
        return new Queue(notificationQueue, true);
    }

    /**
     * Creates JSON message converter
     * @return Jackson JSON converter
     */
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    /**
     * Configures RabbitTemplate with JSON converter
     * @param connectionFactory RabbitMQ connection factory
     * @param messageConverter JSON message converter
     * @return Configured RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}