package com.dbls.app.layer.manager;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.DataProcessorActor;
import com.dbls.app.layer.message.Message;
import lombok.Builder;
import lombok.Value;

public class DataProcessorManagerActor extends AbstractBehavior<Message> {

    private ActorRef<Message> dataProcessor;

    private ActorRef<Message> listenerManager;

    public static Behavior<Message> create() {
        return Behaviors.setup(DataProcessorManagerActor::new);
    }

    /**
     * Ingoing message containing {@link ActorRef} of listenerManagerActor
     */
    @Value
    @Builder
    public static class ListenerManagerInfoMessage implements Message {
        ActorRef<Message> listenerManager;
    }

    public DataProcessorManagerActor(ActorContext<Message> context) {
        super(context);
        context.getLog().info("DataProcessorManagerActor started");

        dataProcessor = getContext().spawn(DataProcessorActor.create(), "data-processor");
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(ListenerManagerInfoMessage.class, this::onListenerManagerInfoMessage)
                .onSignal(PostStop.class, signal -> onPostStop())
                .build();
    }

    private void tellAboutDataProcessor() {
        listenerManager.tell(new DataProcessorInfoMessage(dataProcessor));
    }

    private Behavior<Message> onListenerManagerInfoMessage(ListenerManagerInfoMessage message) {
        getContext().getLog().info("Get ListenerManagerInfoMessage");
        this.listenerManager = message.listenerManager;
        tellAboutDataProcessor();
        return this;
    }

    private Behavior<Message> onPostStop() {
        getContext().getLog().info("DataProcessorManagerActor stopped");
        return this;
    }


}
