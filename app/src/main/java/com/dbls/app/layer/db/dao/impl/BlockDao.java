package com.dbls.app.layer.db.dao.impl;

import com.dbls.app.layer.db.dao.AbstractDao;
import com.dbls.app.layer.db.dao.domain.BlockDm;
import com.dbls.app.layer.db.dao.domain.TransactionDm;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Optional;

public class BlockDao implements AbstractDao<BlockDm> {

    private EntityManager entityManager;

    public BlockDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<BlockDm> get(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<BlockDm> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(BlockDm blockDm) {
        EntityTransaction tx = entityManager.getTransaction();
        try {
            tx.begin();
            entityManager.persist(blockDm);
            tx.commit();
            tx.begin();
            for (TransactionDm transaction : blockDm.getTransactions()) {
                transaction.setBlock(blockDm);
                entityManager.persist(transaction);
            }
            tx.commit();
        }
        catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
    }

    @Override
    public void update(BlockDm blockDm, String[] params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(BlockDm blockDm) {
        throw new UnsupportedOperationException();
    }
}
