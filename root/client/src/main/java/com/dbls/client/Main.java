package com.dbls.client;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import com.dbls.client.actor.BalancerActor;
import com.dbls.client.actor.SearcherActor;
import com.dbls.client.message.ShutdownMessage;
import com.dbls.client.service.configuration.ClientConfiguration;
import com.typesafe.config.ConfigFactory;

public class Main {

    public static void main(String[] args) {
        ClientConfiguration configuration = ClientConfiguration.readConfiguration(args[0]);
        if (args[1].equalsIgnoreCase("searcher")) {
            ActorSystem actorSystem = ActorSystem.create("system", ConfigFactory.load());
            actorSystem.actorOf(SearcherActor.props(), "searcher");
        }
        if(args[1].equalsIgnoreCase("balancer")) {
            ActorSystem actorSystem = ActorSystem.create("system", ConfigFactory.load());
            ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(actorSystem);
            actorSystem.actorOf(ClusterSingletonManager.props(BalancerActor.props(configuration.getRabbitMQConfiguration()), ShutdownMessage.class, settings), "balancer");
        }
    }
}
