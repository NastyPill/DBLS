package com.dbls.client.db.impl;

import com.dbls.client.db.domain.TransactionDm;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class TransactionDao {

    private EntityManager entityManager;

    public TransactionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<TransactionDm> getByBlockNumber(Long blockNumber) {
        Query query = entityManager.createQuery("SELECT t FROM BlockDm b JOIN b.transactions t WHERE b.number=:blockNumber")
                .setParameter("blockNumber", blockNumber);
        return (List<TransactionDm>) query.getResultList();
    }

    public List<TransactionDm> getByFrom(String from) {
        Query query = entityManager.createQuery("SELECT c FROM TransactionDm c WHERE c.from=:from")
                .setParameter("from", from);
        return (List<TransactionDm>) query.getResultList();
    }

    public List<TransactionDm> getByHash(String hash) {
        Query query = entityManager.createQuery("SELECT c FROM TransactionDm c WHERE c.hash=:hash")
                .setParameter("hash", hash);
        return (List<TransactionDm>) query.getResultList();
    }

    public TransactionDm getByBlockAndIndex(Long blockNumber, Long index) {
        Query query = entityManager.createQuery("SELECT t FROM BlockDm b JOIN b.transactions t WHERE b.number=:blockNumber AND t.transactionIndex=:index")
                .setParameter("blockNumber", blockNumber)
                .setParameter("index", index);
        return (TransactionDm) query.getSingleResult();
    }

    public List<TransactionDm> getByTimestamp(Timestamp from, Timestamp to) {
        Query query = entityManager.createQuery("SELECT t FROM BlockDm b JOIN b.transactions t WHERE b.timestamp > :from AND b.timestamp < :to")
                .setParameter("from", from)
                .setParameter("to", to);
        return (List<TransactionDm>) query.getResultList();
    }
}
