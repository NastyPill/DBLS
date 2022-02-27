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
import com.dbls.app.layer.subscriber.BlockchainSubscriber;
import com.dbls.app.layer.subscriber.impl.BlockSubscriber;
import com.dbls.app.layer.subscriber.impl.SmartContractEventSubscriber;
import lombok.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.websocket.events.LogNotification;

/**
 * Actor which listens blockchain node via web3j subscription
 * maps blocks/events into objects and sends them to data processing layer
 */

public class ListenerActor extends AbstractBehavior<Message> {

    private static final Logger LOG = LoggerFactory.getLogger(ListenerActor.class);

    private ActorRef<Message> dataProcessor;

    private BlockchainSubscriber<EthBlock> blockSubscriber;
    private BlockchainSubscriber<LogNotification> smartContractEventSubscriber;

    public static Behavior<Message> create() {
        return Behaviors.setup(ListenerActor::new);
    }

    /**
     * Outgoing message with received data
     */
    @Data
    @AllArgsConstructor
    public static class NewBlockMessage implements ListenerLayerMessage {
        private EthBlock block;
    }

    @Data
    @AllArgsConstructor
    public static class SmartContractLogMessage implements ListenerLayerMessage {
        private LogNotification log;
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
        LOG.info("ListenerActor started");
    }

    private void initSubscribers() {
        blockSubscriber = new BlockSubscriber();
        blockSubscriber.createSubscription(this::handleBlockSubscriberData);
        LOG.info("Transaction subscriber has subscribed");

        smartContractEventSubscriber = new SmartContractEventSubscriber();
        smartContractEventSubscriber.createSubscription(this::handleSmartContractEventSubscriberData);
        LOG.info("Smart Contract Event subscriber has subscribed");
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(DataProcessorInfoMessage.class, this::onDataProcessorInfoMessage)
                .onSignal(PostStop.class, signal -> postStop())
                .build();
    }

    private Behavior<Message> onDataProcessorInfoMessage(DataProcessorInfoMessage message) {
        LOG.info("New dataProcessor ref was accepted");
        this.dataProcessor = message.getDataProcessor();
        initSubscribers();
        return this;
    }

    private void handleBlockSubscriberData(Object blockData) {
        if(blockData instanceof EthBlock) {
            dataProcessor.tell(new NewBlockMessage((EthBlock) blockData));
        } else {
            LOG.warn("Invalid class for incoming block data: " + blockData.getClass());
        }
    }

    private void handleSmartContractEventSubscriberData(Object log) {
        if(log instanceof LogNotification) {
            dataProcessor.tell(new SmartContractLogMessage((LogNotification) log));
        } else {
            LOG.warn("Invalid class for incoming smart contract event data: " + log.getClass());
        }
    }

    private ListenerActor postStop() {
        LOG.info("Listener actor stopped");
        smartContractEventSubscriber.onStop();
        LOG.info("Smart contract events subscription disposed");
        blockSubscriber.onStop();
        LOG.info("Blocks subscription disposed");
        return this;
    }
}
