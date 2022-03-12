package com.dbls.app.layer;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.manager.NewBlockMessage;
import com.dbls.app.layer.manager.SmartContractLogMessage;
import com.dbls.app.layer.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataProcessorActor extends AbstractBehavior<Message> {

    private static final Logger LOG = LoggerFactory.getLogger(DataProcessorActor.class);

    public static Behavior<Message> create() {
        return Behaviors.setup(DataProcessorActor::new);
    }

    public DataProcessorActor(ActorContext<Message> context) {
        super(context);
        context.getLog().info("DataProcessorActor started");
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(SmartContractLogMessage.class, this::onSmartContractLogMessage)
                .onMessage(NewBlockMessage.class, this::onNewBlockMessage)
                .build();
    }

    private Behavior<Message> onNewBlockMessage(NewBlockMessage message) {
        LOG.info("Saving block info");
        return this;
    }

    private Behavior<Message> onSmartContractLogMessage(SmartContractLogMessage message) {
        LOG.info("Saving smart contract log");
        return this;
    }

    private Behavior<Message> handleUnknown(Message message) {
        LOG.info("Received message: " + message.toString());
        return this;
    }
}
