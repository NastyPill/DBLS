package com.dbls.client.db.impl;

import com.dbls.client.db.domain.BlockDm;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

public class BlockDao {

    private EntityManager entityManager;

    public BlockDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public BlockDm getByNumber(Long number) {
        Query query = entityManager.createQuery("SELECT c FROM BlockDm c WHERE c.number=:block_number")
                .setParameter("block_number", number);
        return (BlockDm) query.getSingleResult();
    }

    public BlockDm getByHash(String hash) {
        Query query = entityManager.createQuery("SELECT c FROM BlockDm c WHERE c.hash=:hash")
                .setParameter("hash", hash);
        return (BlockDm) query.getSingleResult();
    }

    public List<BlockDm> getByMiner(String miner) {
        Query query = entityManager.createQuery("SELECT c FROM BlockDm c WHERE c.miner=:miner")
                .setParameter("miner", miner);
        return (List<BlockDm>) query.getResultList();
    }

    public List<BlockDm> getByTimestamp(Timestamp from, Timestamp to) {
        Query query = entityManager.createQuery("SELECT c FROM BlockDm c WHERE c.timestamp > :from AND c.timestamp < :to")
                .setParameter("from", from)
                .setParameter("to", to);
        return (List<BlockDm>) query.getResultList();
    }
}
