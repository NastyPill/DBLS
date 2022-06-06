package com.dbls.app;


import akka.actor.ActorSystem;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import com.dbls.app.layer.DataProcessorActor;
import com.dbls.app.layer.ListenerActor;
import com.dbls.app.layer.message.ShutdownMessage;
import com.dbls.app.layer.supervisor.DBLSSupervisor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args)  {
        if(args[0].equals("listener")) {
            ActorSystem actorSystem = ActorSystem.create("system", ConfigFactory.load());
            actorSystem.actorOf(DBLSSupervisor.props());
        } else if(args[0].equals("dataProcessor")) {
            ActorSystem actorSystem = ActorSystem.create("system", ConfigFactory.load());
            ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(actorSystem);
            actorSystem.actorOf(ClusterSingletonManager.props(DataProcessorActor.props(), ShutdownMessage.class, settings), "dataProcessor");
        }
    }

}
