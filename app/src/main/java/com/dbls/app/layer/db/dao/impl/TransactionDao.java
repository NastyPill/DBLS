package com.dbls.app.layer.db.dao.impl;

import com.dbls.app.layer.db.dao.AbstractDao;
import com.dbls.app.layer.db.dao.domain.TransactionDm;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class TransactionDao implements AbstractDao<TransactionDm> {

    private EntityManager entityManager;

    public TransactionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<TransactionDm> get(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<TransactionDm> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(TransactionDm transaction) {
        entityManager.persist(transaction);
    }

    @Override
    public void update(TransactionDm transactionDao, String[] params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(TransactionDm transactionDao) {
        throw new UnsupportedOperationException();
    }
}
