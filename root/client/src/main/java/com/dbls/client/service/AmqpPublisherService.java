package com.dbls.client.service;

import com.dbls.client.model.AmqpResponse;
import com.dbls.client.service.configuration.RabbitMQConfiguration;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.TimeoutException;

public class AmqpPublisherService {

    private RabbitMQConfiguration configuration;
    private Channel channel;

    public AmqpPublisherService(RabbitMQConfiguration configuration) throws IOException, TimeoutException {
        this.configuration = configuration;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(configuration.getPort());
        connectionFactory.setHost(configuration.getHost());
        channel = connectionFactory.newConnection().createChannel();
        channel.queueDeclare(configuration.getPublishingQueue(), false, false, false, null);
    }

    public void publish(AmqpResponse response) throws IOException {
        channel.basicPublish("", configuration.getPublishingQueue(), null, response.toString().getBytes());
    }
}
