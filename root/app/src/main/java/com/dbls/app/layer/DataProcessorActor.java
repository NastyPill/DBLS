package com.dbls.app.layer;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import com.dbls.app.layer.manager.NewBlockMessage;
import com.dbls.app.layer.manager.SmartContractLogMessage;
import com.dbls.app.layer.message.Message;
import com.dbls.app.layer.service.DataProcessingService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.websocket.events.Log;

import java.util.LinkedList;
import java.util.Queue;

public class DataProcessorActor extends AbstractBehavior<Message> {

    private DataProcessingService dataProcessingService;

    private Queue<String> blockHashQueue;
    private Queue<LogIdentifier> logQueue;

    private static final Logger LOG = LoggerFactory.getLogger(DataProcessorActor.class);

    public static Behavior<Message> create() {
        return Behaviors.setup(DataProcessorActor::new);
    }

    public DataProcessorActor(ActorContext<Message> context) {
        super(context);
        this.dataProcessingService = new DataProcessingService();
        context.getLog().info("DataProcessorActor started");
        blockHashQueue = new LinkedList<>();
        logQueue = new LinkedList<>();
    }

    @Override
    public Receive<Message> createReceive() {
        return newReceiveBuilder()
                .onMessage(SmartContractLogMessage.class, this::onSmartContractLogMessage)
                .onMessage(NewBlockMessage.class, this::onNewBlockMessage)
                .build();
    }

    private Behavior<Message> onNewBlockMessage(NewBlockMessage message) {
        EthBlock.Block block = message.getBlock().getBlock();
        LOG.info("Get block for saving with number: " + block.getNumber());
        if(blockHashQueue.size() > 30) {
            blockHashQueue.poll();
        }
        if(!blockHashQueue.contains(block.getHash())) {
            blockHashQueue.add(block.getHash());
            dataProcessingService.saveNewBlock(block);
        }
        return this;
    }

    private Behavior<Message> onSmartContractLogMessage(SmartContractLogMessage message) {
        Log log = message.getLog().getParams().getResult();
        if(logQueue.size() > 5000) {
            logQueue.poll();
        }
        LogIdentifier logIdentifier = new LogIdentifier(log.getBlockHash(), log.getTransactionHash());
        if(!logQueue.contains(logIdentifier)) {
            logQueue.add(logIdentifier);
            dataProcessingService.saveNewLog(log);
        }
        return this;
    }

    private Behavior<Message> handleUnknown(Message message) {
        LOG.info("Received message: " + message.toString());
        return this;
    }

    @Value
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class LogIdentifier {
        String blockHash;
        String transactionHash;
    }
}
