package com.dbls.client.service;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.service.configuration.RabbitMQConfiguration;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public class AmqpConsumerService {

    private static Logger LOG = LoggerFactory.getLogger(AmqpConsumerService.class);

    private RabbitMQConfiguration configuration;
    private ConnectionFactory connectionFactory;

    private Queue<AmqpRequest> queue;


    public AmqpConsumerService(RabbitMQConfiguration configuration) {
        this.configuration = configuration;
        queue = new LinkedList<>();
        connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(configuration.getPort());
        connectionFactory.setHost(configuration.getHost());
    }

    public void startConsumption() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(configuration.getConsumableQueue(), false, false, false, null);
        channel.basicConsume(configuration.getConsumableQueue(), false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                LOG.info(new String(body));
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }

}
