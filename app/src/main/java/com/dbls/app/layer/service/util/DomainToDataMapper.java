package com.dbls.app.layer.service.util;

import com.dbls.app.layer.db.dao.domain.BlockDm;
import com.dbls.app.layer.db.dao.domain.TransactionDm;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.stream.Collectors;

public class DomainToDataMapper {

    public static BlockDm mapToBlockDm(EthBlock.Block block) {
        return BlockDm.builder()
                .author(block.getAuthor())
                .difficulty(block.getDifficultyRaw())
                .extraData(block.getExtraData())
                .gasLimit(block.getGasLimitRaw())
                .gasUsed(block.getGasUsedRaw())
                .hash(block.getHash())
                .logsBloom(block.getLogsBloom())
                .miner(block.getMiner())
                .mixHash(block.getMixHash())
                .nonce(block.getNonceRaw())
                .number(block.getNumber().longValue())
                .parentHash(block.getParentHash())
                .receiptsRoot(block.getReceiptsRoot())
                .sha3Uncles(block.getSha3Uncles())
                .size(block.getSizeRaw())
                .stateRoot(block.getStateRoot())
                .timestamp(block.getTimestampRaw())
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
                .gas(transaction.getGasRaw())
                .gasPrice(transaction.getGasPriceRaw())
                .hash(transaction.getHash())
                .transactionIndex(transaction.getTransactionIndexRaw())
                .input(transaction.getInput())
                .r(transaction.getR())
                .nonce(transaction.getNonceRaw())
                .publicKey(transaction.getPublicKey())
                .raw(transaction.getRaw())
                .value(transaction.getValueRaw())
                .v(transaction.getV())
                .build();
    }

}
