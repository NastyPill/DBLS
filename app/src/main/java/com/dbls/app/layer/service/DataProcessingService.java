package com.dbls.app.layer.service;

import com.dbls.app.layer.db.dao.AbstractDao;
import com.dbls.app.layer.db.dao.domain.BlockDm;
import com.dbls.app.layer.db.dao.domain.LogDm;
import com.dbls.app.layer.db.dao.domain.TransactionDm;
import com.dbls.app.layer.db.dao.impl.BlockDao;
import com.dbls.app.layer.db.dao.impl.LogDao;
import com.dbls.app.layer.db.dao.impl.TransactionDao;
import com.dbls.app.layer.service.util.DomainToDataMapper;
import org.web3j.protocol.core.methods.response.EthBlock;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.websocket.events.Log;
import org.web3j.protocol.websocket.events.LogNotification;

import static com.dbls.app.layer.service.util.DomainToDataMapper.mapToBlockDm;

public class DataProcessingService {

    private AbstractDao<BlockDm> blockDao;
    private AbstractDao<LogDm> logDao;
    private AbstractDao<TransactionDm> transactionDao;

    public DataProcessingService() {
        this.blockDao = new BlockDao();
        this.logDao = new LogDao();
        this.transactionDao = new TransactionDao();
    }

    public void saveNewBlock(EthBlock.Block block) {
        blockDao.save(mapToBlockDm(block));
    }

    public void saveNewTransaction(Transaction transaction) {
        throw new UnsupportedOperationException();
    }

    public void saveNewLog(Log log) {
        throw new UnsupportedOperationException();
    }
}
