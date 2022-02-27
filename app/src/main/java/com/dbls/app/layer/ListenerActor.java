package com.dbls.app.layer;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.message.DataProcessingLayerMessage;
import com.dbls.app.layer.message.ListenerLayerMessage;
import com.dbls.app.layer.message.Message;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Actor which listens blockchain node via web3j subscription
 * maps blocks/events into objects and sends them to data processing layer
 */

public class ListenerActor extends AbstractBehavior<Message> {

    private ActorRef<Message> dataProcessor;

    public static Behavior<Message> create() {
        return Behaviors.setup(ListenerActor::new);
    }

    /**
     * Outgoing message with received data
     */
    public class NewDataMessage implements ListenerLayerMessage {
        //todo() add fields
    }

    /**
     * Incoming message with {@link ActorRef} of current running dataProcessorActor
     */
    @Value
    @Builder
    public static class DataProcessorInfoMessage implements DataProcessingLayerMessage {
        @NonNull
        ActorRef<Message> dataProcessor;
    }

    public ListenerActor(ActorContext<Message> context) {
        super(context);
        context.getLog().info("ListenerActor started");
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(DataProcessorInfoMessage.class, message -> onDataProcessorInfoMessage(message))
                .onSignal(PostStop.class, signal -> postStop())
                .build();
    }

    private Behavior<Message> onDataProcessorInfoMessage(DataProcessorInfoMessage message) {
        getContext().getLog().info("New dataProcessor ref was accepted");
        this.dataProcessor = message.getDataProcessor();
        return this;
    }

    private ListenerActor postStop() {
        getContext().getLog().info("Listener actor stopped");
        return this;
    }
}
