package com.dbls.client.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.dbls.client.message.NewWorkRequest;
import com.dbls.client.message.NewWorkResponse;
import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.service.AmqpConsumerService;
import com.dbls.client.service.AmqpPublisherService;
import com.dbls.client.service.configuration.RabbitMQConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class BalancerActor extends AbstractActor {

    private AmqpConsumerService amqpConsumerService;
    private AmqpPublisherService amqpPublisherService;

    private static final Logger LOG = LoggerFactory.getLogger(BalancerActor.class);

    public static Props props(RabbitMQConfiguration rabbitMQConfiguration) {
        return Props.create(BalancerActor.class, rabbitMQConfiguration);
    }

    public BalancerActor(RabbitMQConfiguration rabbitMQConfiguration) {
        super();
        initialize(rabbitMQConfiguration);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(NewWorkRequest.class, this::handleNewWorkRequest)
                .build();
    }

    private void initialize(RabbitMQConfiguration rabbitMQConfiguration) {
        try {
            this.amqpConsumerService = new AmqpConsumerService(rabbitMQConfiguration);
            this.amqpPublisherService = new AmqpPublisherService(rabbitMQConfiguration);
        } catch (IOException | TimeoutException e) {
            LOG.error(e.getMessage());
        }
        LOG.info("BalancerActor starts");
    }

    private void handleNewWorkRequest(NewWorkRequest msg) throws IOException {
        AmqpResponse amqpResponse = msg.getAmqpResponse();
        LOG.trace("New work request was received");
        if (amqpResponse != null) {
            LOG.info("New work request with AmqpResponse was received");
            amqpPublisherService.publish(amqpResponse);
        }
        AmqpRequest amqpRequest = amqpConsumerService.getAmqpRequest();
        getSender().tell(new NewWorkResponse(amqpRequest), getSelf());
    }
}
