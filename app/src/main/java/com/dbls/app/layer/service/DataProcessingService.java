package com.dbls.app.layer.service;

import com.dbls.app.layer.db.dao.AbstractDao;
import com.dbls.app.layer.db.dao.domain.BlockDm;
import com.dbls.app.layer.db.dao.domain.LogDm;
import com.dbls.app.layer.db.dao.domain.TransactionDm;
import com.dbls.app.layer.db.dao.impl.BlockDao;
import com.dbls.app.layer.db.dao.impl.LogDao;
import com.dbls.app.layer.db.dao.impl.TransactionDao;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.websocket.events.Log;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static com.dbls.app.layer.service.util.DomainToDataMapper.mapToBlockDm;
import static com.dbls.app.layer.service.util.DomainToDataMapper.mapToLogDm;

public class DataProcessingService {

    private AbstractDao<BlockDm> blockDao;
    private AbstractDao<LogDm> logDao;
    private AbstractDao<TransactionDm> transactionDao;

    public DataProcessingService() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        this.blockDao = new BlockDao(entityManager);
        this.logDao = new LogDao(entityManager);
        this.transactionDao = new TransactionDao(entityManager);
    }

    public void saveNewBlock(EthBlock.Block block) {
        blockDao.save(mapToBlockDm(block));
    }

    public void saveNewLog(Log log) {
        logDao.save(mapToLogDm(log));
    }
}
