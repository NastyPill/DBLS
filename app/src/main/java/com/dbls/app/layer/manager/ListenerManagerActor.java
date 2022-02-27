package com.dbls.app.layer.manager;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.ListenerActor;
import com.dbls.app.layer.message.Message;

public class ListenerManagerActor extends AbstractBehavior<Message> {

    private ActorRef<Message> listener;

    public static Behavior<Message> create() {
        return Behaviors.setup(ListenerManagerActor::new);
    }

    public ListenerManagerActor(ActorContext<Message> context) {
        super(context);
        context.getLog().info("ListenerManagerActor started");

        //todo add number of listenerActors configuration possibility
        context.getLog().info("Starting ListenerActors");
        listener = context.spawn(ListenerActor.create(), "listener");
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(ListenerActor.DataProcessorInfoMessage.class, message -> notifyAllListenersAboutDataProcessor(message))
                .onSignal(PostStop.class, signal -> postStop())
                .build();
    }

    private Behavior<Message> notifyAllListenersAboutDataProcessor(ListenerActor.DataProcessorInfoMessage message) {
        getContext().getLog().info("Get DataProcessorInfoMessage");
        listener.tell(message);
        return this;
    }

    private Behavior<Message> postStop() {
        getContext().getLog().info("ListenerManagerActor stopped");
        return this;
    }
}
