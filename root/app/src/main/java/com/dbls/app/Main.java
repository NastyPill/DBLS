package com.dbls.app;


import akka.actor.ActorSystem;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import com.dbls.app.layer.DataProcessorActor;
import com.dbls.app.layer.ListenerActor;
import com.dbls.app.layer.message.ShutdownMessage;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args)  {
        if(args[0].contains("listener")) {
            ActorSystem actorSystem = ActorSystem.create("system", ConfigFactory.load().getConfig("listener"));
            actorSystem.actorOf(ListenerActor.props());
        } else if(args[0].equals("dataProcessor1")) {
            ActorSystem actorSystem = ActorSystem.create("system", ConfigFactory.load().getConfig("dataProcessor1"));
            ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(actorSystem);
            actorSystem.actorOf(ClusterSingletonManager.props(DataProcessorActor.props(), ShutdownMessage.class, settings), "dataProcessor");
        } else if(args[0].equals("dataProcessor2")) {
            ActorSystem actorSystem = ActorSystem.create("system", ConfigFactory.load().getConfig("dataProcessor2"));
            ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(actorSystem);
            actorSystem.actorOf(ClusterSingletonManager.props(DataProcessorActor.props(), ShutdownMessage.class, settings), "dataProcessor");
        }
    }

}
