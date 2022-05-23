package com.dbls.app.layer.db.dao.impl;

import com.dbls.app.layer.db.dao.AbstractDao;
import com.dbls.app.layer.db.dao.domain.LogDm;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class LogDao implements AbstractDao<LogDm> {

    private EntityManager entityManager;

    public LogDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<LogDm> get(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<LogDm> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void save(LogDm logDm) {
        entityManager.persist(logDm);
    }

    @Override
    public void update(LogDm logDm, String[] params) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(LogDm logDm) {
        throw new UnsupportedOperationException();
    }
}
