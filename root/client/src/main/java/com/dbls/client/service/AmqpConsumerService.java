package com.dbls.client.service;

import com.dbls.client.model.AmqpBody;
import com.dbls.client.model.AmqpRequest;
import com.dbls.client.service.configuration.RabbitMQConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

public class AmqpConsumerService {

    private static Logger LOG = LoggerFactory.getLogger(AmqpConsumerService.class);

    private ObjectMapper objectMapper;
    private RabbitMQConfiguration configuration;
    private Channel channel;


    public AmqpConsumerService(RabbitMQConfiguration configuration) throws IOException, TimeoutException {
        this.configuration = configuration;
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setPort(configuration.getPort());
        connectionFactory.setHost(configuration.getHost());
        this.channel = initialize(connectionFactory);
        objectMapper = new ObjectMapper();
        LOG.info("AmqpConsumerService successfully started");
    }

    private Channel initialize(ConnectionFactory connectionFactory) throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(configuration.getConsumableQueue(), false, false, false, null);
        return channel;
    }

    public AmqpRequest getAmqpRequest() throws IOException {
        LOG.trace("getAmqpRequest");
        GetResponse response = channel.basicGet(configuration.getConsumableQueue(), true);
        if(response == null) {
            return null;
        }
        LOG.info("getAmqpRequest: smthng was found");
        LOG.info(new String(response.getBody()));
        return objectMapper.readValue(new String(response.getBody()), AmqpRequest.class);
    }
}
