package com.dbls.client.actor;

import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Receive;
import com.dbls.client.message.Message;

public class BalancerActor extends AbstractBehavior<Message> {

    public BalancerActor(ActorContext<Message> context) {
        super(context);
    }

    @Override
    public Receive<Message> createReceive() {
        return null;
    }
}
