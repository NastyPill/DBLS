package com.dbls.app.layer;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.websocket.events.LogNotification;

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
                .onMessage(ListenerActor.SmartContractLogMessage.class, this::onSmartContractLogMessage)
                .onMessage(ListenerActor.NewBlockMessage.class, this::onNewBlockMessage)
                .build();
    }

    private Behavior<Message> onNewBlockMessage(ListenerActor.NewBlockMessage message) {
        EthBlock.Block block = message.getBlock().getBlock();
        LOG.info("BLOCK: " + block.getAuthor());
        LOG.info("BLOCK: " + block.getExtraData());
        LOG.info("BLOCK: " + block.getHash());
        for (EthBlock.TransactionResult t: block.getTransactions()) {
            Object info = t.get();
            if(info instanceof String) {
                LOG.info("TRANSACTION HASH: " + info);
            } else if (info instanceof Transaction) {
                LOG.info("TRANSACTION: " + ((Transaction) info).getFrom());
                LOG.info("TRANSACTION: " + ((Transaction) info).getTo());
                LOG.info("TRANSACTION: " + ((Transaction) info).getGas());
            }
        }
        return this;
    }

    private Behavior<Message> onSmartContractLogMessage(ListenerActor.SmartContractLogMessage message) {
        LogNotification logNotification = message.getLog();
        LOG.info("METHOD: " + logNotification.getMethod());
        LOG.info("SUBS: " + logNotification.getParams().getSubsciption());
        LOG.info("DATA: " + logNotification.getParams().getResult().getData());
        for (String topic : logNotification.getParams().getResult().getTopics()) {
            LOG.info("TOPIC: " + topic);
        }
        return this;
    }

    private Behavior<Message> handleUnknown(Message message) {
        LOG.info("Received message: " + message.toString());
        return this;
    }
}
