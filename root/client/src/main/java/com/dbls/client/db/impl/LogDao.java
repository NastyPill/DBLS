package com.dbls.client.db.impl;

import com.dbls.client.db.domain.LogDm;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

public class LogDao {

    private EntityManager entityManager;

    public LogDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<LogDm> getByAddress(String address) {
        Query query = entityManager.createQuery("SELECT c FROM LogDm c WHERE c.address=:address")
                .setParameter("address", address);
        return (List<LogDm>) query.getResultList();
    }

    public List<LogDm> getByBlockHash(String blockHash) {
        Query query = entityManager.createQuery("SELECT c FROM LogDm c WHERE c.blockHash=:blockHash")
                .setParameter("blockHash", blockHash);
        return (List<LogDm>) query.getResultList();
    }

    public List<LogDm> getByBlockNumber(Long blockNumber) {
        Query query = entityManager.createQuery("SELECT c FROM LogDm c WHERE c.blockNumber=:blockNumber")
                .setParameter("blockNumber", blockNumber);
        return (List<LogDm>) query.getResultList();
    }

    public List<LogDm> getByTransactionHash(String transactionHash) {
        Query query = entityManager.createQuery("SELECT c FROM LogDm c WHERE c.transactionHash=:transactionHash")
                .setParameter("transactionHash", transactionHash);
        return (List<LogDm>) query.getResultList();
    }
}
