package ru.sivak.infrastructure.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_EXCHANGE = "order.exchange";

    public static final String ORDER_APPROVED_QUEUE = "order.approved.queue";
    public static final String ORDER_REJECTED_QUEUE = "order.rejected.queue";

    public static final String ROUTING_KEY_APPROVED = "order.approved";
    public static final String ROUTING_KEY_REJECTED = "order.rejected";
    public static final String ROUTING_KEY_SENT_FOR_APPROVAL = "order.sent-for-approval";

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(ORDER_EXCHANGE, true, false);
    }

    @Bean
    public Queue orderApprovedQueue() {
        return QueueBuilder.durable(ORDER_APPROVED_QUEUE).build();
    }

    @Bean
    public Queue orderRejectedQueue() {
        return QueueBuilder.durable(ORDER_REJECTED_QUEUE).build();
    }

    @Bean
    public Binding orderApprovedBinding(Queue orderApprovedQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderApprovedQueue).to(orderExchange).with(ROUTING_KEY_APPROVED);
    }

    @Bean
    public Binding orderRejectedBinding(Queue orderRejectedQueue, DirectExchange orderExchange) {
        return BindingBuilder.bind(orderRejectedQueue).to(orderExchange).with(ROUTING_KEY_REJECTED);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            MessageConverter messageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
