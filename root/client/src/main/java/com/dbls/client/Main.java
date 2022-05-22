package com.dbls.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.dbls.client.actor.BalancerActor;
import com.dbls.client.actor.SearcherActor;
import com.dbls.client.service.configuration.ClientConfiguration;

public class Main {

    public static void main(String[] args) {
        ClientConfiguration configuration = ClientConfiguration.readConfiguration(args[0]);
        ActorSystem actorSystem = ActorSystem.create();
        ActorRef balancer = actorSystem.actorOf(BalancerActor.props(configuration.getRabbitMQConfiguration()), "balancer");
        actorSystem.actorOf(SearcherActor.props(balancer), "searcher");
    }
}
