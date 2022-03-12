package com.dbls.app.layer.db.dao.impl;

import com.dbls.app.layer.db.dao.AbstractDao;
import com.dbls.app.layer.db.dao.domain.BlockDm;

import java.util.List;
import java.util.Optional;

public class BlockDao implements AbstractDao<BlockDm> {

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
        throw new UnsupportedOperationException();
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
