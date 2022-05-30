package com.dbls.app.layer;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.dbls.app.layer.message.NewBlockMessage;
import com.dbls.app.layer.message.SmartContractLogMessage;
import com.dbls.app.layer.message.Message;
import com.dbls.app.layer.service.DataProcessingService;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.websocket.events.Log;

import java.util.LinkedList;
import java.util.Queue;

public class DataProcessorActor extends AbstractActor {

    private DataProcessingService dataProcessingService;

    private Queue<String> blockHashQueue;
    private Queue<LogIdentifier> logQueue;

    private static final Logger LOG = LoggerFactory.getLogger(DataProcessorActor.class);

    public static Props props() {
        return Props.create(DataProcessorActor.class);
    }

    public DataProcessorActor() {
        this.dataProcessingService = new DataProcessingService();
        blockHashQueue = new LinkedList<>();
        logQueue = new LinkedList<>();
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SmartContractLogMessage.class, this::onSmartContractLogMessage)
                .match(NewBlockMessage.class, this::onNewBlockMessage)
                .build();
    }

    private void onNewBlockMessage(NewBlockMessage message) {
        EthBlock.Block block = message.getBlock().getBlock();
        LOG.info("Get block for saving with number: " + block.getNumber());
        if(blockHashQueue.size() > 100) {
            blockHashQueue.poll();
        }
        if(!blockHashQueue.contains(block.getHash())) {
            blockHashQueue.add(block.getHash());
            log(block);
            dataProcessingService.saveNewBlock(block);
        }
    }

    private void log(EthBlock.Block block) {
        LOG.warn("============================");
        LOG.warn("============================");
        LOG.warn("============================");
        //TODO() remove logic to service + add transaction validation
        LOG.warn(block.getHash());
        LOG.warn(block.getNumber().toString());
        for (EthBlock.TransactionResult<Transaction> transactionResult: block.getTransactions()) {
            Transaction transaction = transactionResult.get();
            LOG.warn(transaction.getHash());
        }
        LOG.warn("============================");
        LOG.warn("============================");
        LOG.warn("============================");
    }

    private void onSmartContractLogMessage(SmartContractLogMessage message) {
        Log log = message.getLog().getParams().getResult();
        if(logQueue.size() > 20000) {
            logQueue.poll();
        }
        LogIdentifier logIdentifier = new LogIdentifier(log.getBlockHash(), log.getTransactionHash());
        if(!logQueue.contains(logIdentifier)) {
            logQueue.add(logIdentifier);
            dataProcessingService.saveNewLog(log);
        }
    }

    private void handleUnknown(Message message) {
        LOG.info("Received message: " + message.toString());
    }

    @Value
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class LogIdentifier {
        String blockHash;
        String transactionHash;
    }
}
