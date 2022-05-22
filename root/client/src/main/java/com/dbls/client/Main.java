package com.dbls.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.dbls.client.actor.BalancerActor;
import com.dbls.client.actor.SearcherActor;
import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.model.RequestType;
import com.dbls.client.service.AmqpConsumerService;
import com.dbls.client.service.AmqpPublisherService;
import com.dbls.client.service.configuration.ClientConfiguration;
import com.dbls.client.service.configuration.RabbitMQConfiguration;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) {
        ClientConfiguration configuration = ClientConfiguration.readConfiguration(args[0]);
        ActorSystem actorSystem = ActorSystem.create();
        ActorRef balancer = actorSystem.actorOf(BalancerActor.props(configuration.getRabbitMQConfiguration()), "balancer");
        actorSystem.actorOf(SearcherActor.props(balancer), "searcher");
    }
}
