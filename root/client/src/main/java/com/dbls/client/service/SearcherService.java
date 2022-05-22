package com.dbls.client.service;

import com.dbls.client.db.impl.BlockDao;
import com.dbls.client.db.impl.LogDao;
import com.dbls.client.db.impl.TransactionDao;
import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.dbls.client.model.RequestType.*;

public class SearcherService {

    private static Logger LOG = LoggerFactory.getLogger(SearcherService.class);

    private ObjectMapper objectMapper;
    private BlockDao blockDao;
    private TransactionDao transactionDao;
    private LogDao logDao;

    public SearcherService() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        this.blockDao = new BlockDao(entityManager);
        this.transactionDao = new TransactionDao(entityManager);
        this.logDao = new LogDao(entityManager);
        objectMapper = new ObjectMapper();
    }
    
    public AmqpResponse executeQuery(AmqpRequest amqpRequest) throws JsonProcessingException {
        if(amqpRequest.getType() == BLOCK_BY_HASH) {
            if(amqpRequest.getBlockHash() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Block hash was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(blockDao.getByHash(amqpRequest.getBlockHash())));
        }
        if(amqpRequest.getType() == BLOCK_BY_MINER) {
            if(amqpRequest.getMiner() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Block miner was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(blockDao.getByMiner(amqpRequest.getMiner())));
        }
        if(amqpRequest.getType() == BLOCK_BY_NUMBER) {
            if(amqpRequest.getBlockNumber() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Block number was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(blockDao.getByNumber(Long.parseLong(amqpRequest.getBlockNumber()))));
        }
        if(amqpRequest.getType() == BLOCK_BY_TIMERANGE) {
            if(amqpRequest.getFromTime() == null || amqpRequest.getToTime() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "One of timerange borders was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(blockDao.getByTimestamp(amqpRequest.getFromTime(), amqpRequest.getToTime())));
        }
        if(amqpRequest.getType() == LOG_BY_ADDRESS) {
            if(amqpRequest.getAddress() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Log address was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(logDao.getByAddress(amqpRequest.getAddress())));
        }
        if(amqpRequest.getType() == LOG_BY_BLOCK_HASH) {
            if(amqpRequest.getBlockHash() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Block hash was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(logDao.getByBlockHash(amqpRequest.getBlockHash())));
        }
        if(amqpRequest.getType() == LOG_BY_BLOCK_NUMBER) {
            if(amqpRequest.getBlockNumber() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Block number was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(logDao.getByBlockNumber(Long.parseLong(amqpRequest.getBlockNumber()))));
        }
        if(amqpRequest.getType() == LOG_BY_TRANSACTION_HASH) {
            if(amqpRequest.getTransactionHash() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Transaction number was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(logDao.getByTransactionHash(amqpRequest.getTransactionHash())));
        }
        if(amqpRequest.getType() == TRANSACTION_BY_BLOCK_NUMBER) {
            if(amqpRequest.getBlockNumber() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Block number was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(transactionDao.getByBlockNumber(Long.parseLong(amqpRequest.getBlockNumber()))));
        }
        if(amqpRequest.getType() == TRANSACTION_BY_FROM) {
            if(amqpRequest.getFrom() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Transaction from was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(transactionDao.getByFrom(amqpRequest.getFrom())));
        }
        if(amqpRequest.getType() == TRANSACTION_BY_HASH) {
            if(amqpRequest.getTransactionHash() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Transaction hash was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(transactionDao.getByHash(amqpRequest.getTransactionHash())));
        }
        if(amqpRequest.getType() == TRANSACTION_BY_NUMBER_AND_BLOCK) {
            if(amqpRequest.getBlockNumber() == null || amqpRequest.getTransactionNumber() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "Block number or transaction index was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(transactionDao.getByBlockAndIndex(
                    Long.parseLong(amqpRequest.getBlockNumber()),
                    Long.parseLong(amqpRequest.getTransactionNumber()))));
        }
        if(amqpRequest.getType() == TRANSACTION_BY_TIMERANGE) {
            if(amqpRequest.getFromTime() == null || amqpRequest.getToTime() == null) {
                return new AmqpResponse(amqpRequest.getUuid(), "One of timerange borders was null");
            }
            return new AmqpResponse(amqpRequest.getUuid(), objectMapper.writeValueAsString(
                    transactionDao.getByTimestamp(amqpRequest.getFromTime(),
                            amqpRequest.getToTime())));
        }
        return new AmqpResponse(amqpRequest.getUuid(), "Wrong request type");
    }

}
