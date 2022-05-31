package com.dbls.app.layer.supervisor;

import akka.actor.*;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import akka.japi.pf.DeciderBuilder;
import com.dbls.app.layer.ListenerActor;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class DBLSSupervisor extends AbstractActor {

    private static final SupervisorStrategy strategy = new OneForOneStrategy(
            -1,
            Duration.Inf(),
            DeciderBuilder.match(Throwable.class, a -> SupervisorStrategy.restart())
                    .build());

    public static Props props() {
        return Props.create(DBLSSupervisor.class);
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder().build();
    }

    @Override
    public void preStart() throws Exception {
        ActorSystem system = getContext().getSystem();
        ClusterSingletonProxySettings proxySettings =
                ClusterSingletonProxySettings.create(system);
        ActorRef dataProcessor = system.actorOf(ClusterSingletonProxy.props("/user/dataProcessor", proxySettings), "dataProcessorProxy");
        getContext().actorOf(ListenerActor.props(dataProcessor));
    }

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
