package com.dbls.app.layer.supervisor;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.manager.DataProcessorManagerActor;
import com.dbls.app.layer.manager.ListenerManagerActor;
import com.dbls.app.layer.message.Message;

public class DBLSSupervisor extends AbstractBehavior<Void> {

    private ActorRef<Message> listenerManagerActor;
    private ActorRef<Message> dataProcessorManagerActor;

    public static Behavior<Void> create() {
        return Behaviors.setup(DBLSSupervisor::new);
    }

    private DBLSSupervisor(ActorContext<Void> context) {
        super(context);
        context.getLog().info("DBLS started");

        listenerManagerActor = context.spawn(ListenerManagerActor.create(), "listener-manager");
        dataProcessorManagerActor = context.spawn(DataProcessorManagerActor.create(), "dataProcessor-manager");

        dataProcessorManagerActor
                .tell(
                        DataProcessorManagerActor.ListenerManagerInfoMessage
                                .builder()
                                .listenerManager(listenerManagerActor)
                                .build()
                );
    }

    @Override
    public Receive<Void> createReceive() {
        return newReceiveBuilder().onSignal(PostStop.class, signal -> onPostStop()).build();
    }

    private DBLSSupervisor onPostStop() {
        getContext().getLog().info("DBLS stopped");
        return this;
    }
}
