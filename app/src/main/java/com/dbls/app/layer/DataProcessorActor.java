package com.dbls.app.layer;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.message.Message;

public class DataProcessorActor extends AbstractBehavior<Message> {

    public static Behavior<Message> create() {
        return Behaviors.setup(DataProcessorActor::new);
    }

    public DataProcessorActor(ActorContext<Message> context) {
        super(context);
        context.getLog().info("DataProcessorActor started");
    }

    @Override
    public Receive<Message> createReceive() {
        return null;
    }
}
