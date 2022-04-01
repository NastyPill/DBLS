package com.dbls.app.layer.service.util;

import com.dbls.app.layer.db.dao.domain.BlockDm;
import com.dbls.app.layer.db.dao.domain.LogDm;
import com.dbls.app.layer.db.dao.domain.TransactionDm;
import org.postgresql.util.PGTimestamp;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.websocket.events.Log;

import java.sql.Timestamp;
import java.util.stream.Collectors;

public class DomainToDataMapper {

    public static BlockDm mapToBlockDm(EthBlock.Block block) {
        return BlockDm.builder()
                .author(block.getAuthor())
                .difficulty(block.getDifficultyRaw())
                .extraData(block.getExtraData())
                .gasLimit(block.getGasLimit().longValue())
                .gasUsed(block.getGasUsed().longValue())
                .hash(block.getHash())
                .logsBloom(block.getLogsBloom())
                .miner(block.getMiner())
                .mixHash(block.getMixHash())
                .nonce(block.getNonceRaw())
                .number(block.getNumber().longValue())
                .parentHash(block.getParentHash())
                .receiptsRoot(block.getReceiptsRoot())
                .sha3Uncles(block.getSha3Uncles())
                .size(block.getSize().longValue())
                .stateRoot(block.getStateRoot())
                .timestamp(new Timestamp(block.getTimestamp().longValue() * 1000))
                .totalDifficulty(block.getTotalDifficultyRaw())
                .transactions(block.getTransactions().stream().map(DomainToDataMapper::mapToTransactionDm).collect(Collectors.toList()))
                .transactionsRoot(block.getTransactionsRoot())
                .build();
    }

    public static TransactionDm mapToTransactionDm(EthBlock.TransactionResult<Transaction> transactionResult) {
        Transaction transaction = transactionResult.get();
        return TransactionDm.builder()
                .s(transaction.getS())
                .creates(transaction.getCreates())
                .from(transaction.getFrom())
                .gas(transaction.getGas().longValue())
                .gasPrice(transaction.getGasPrice().longValue())
                .hash(transaction.getHash())
                .transactionIndex(transaction.getTransactionIndex().longValue())
                .input(transaction.getInput())
                .r(transaction.getR())
                .nonce(transaction.getNonce().longValue())
                .publicKey(transaction.getPublicKey())
                .raw(transaction.getRaw())
                .value(transaction.getValue().longValue())
                .v(transaction.getV())
                .build();
    }

    public static LogDm mapToLogDm(Log log) {
        LogDm logDm = LogDm.builder()
                .data(log.getData())
                .address(log.getAddress())
                .blockHash(log.getBlockHash())
                .blockNumber(Long.parseLong(log.getBlockNumber().substring(2), 16))
                .transactionHash(log.getTransactionHash())
                .transactionIndex(Long.parseLong(log.getTransactionIndex().substring(2), 16))
                .logIndex(Long.parseLong(log.getLogIndex().substring(2), 16))
                .build();
        logDm.setTopics(log.getTopics());
        return logDm;
    }

}
