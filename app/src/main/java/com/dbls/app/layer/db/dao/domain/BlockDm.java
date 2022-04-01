package com.dbls.app.layer.db.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.postgresql.util.PGTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "block", schema = "public")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockDm {

    @Id
    private long number;
    private String hash;
    @Column(name = "parent_hash")
    private String parentHash;
    private String nonce;
    @Column(name = "sha_3_uncles")
    private String sha3Uncles;
    @Column(name = "logs_bloom")
    private String logsBloom;
    @Column(name = "transactions_root")
    private String transactionsRoot;
    @Column(name = "state_root")
    private String stateRoot;
    @Column(name = "receipts_root")
    private String receiptsRoot;
    private String author;
    private String miner;
    @Column(name = "mix_hash")
    private String mixHash;
    private String difficulty;
    @Column(name = "total_difficulty")
    private String totalDifficulty;
    @Column(name = "extra_data")
    private String extraData;
    @Column(name = "size_")
    private long size;
    @Column(name = "gas_limit")
    private long gasLimit;
    @Column(name = "gas_used") // todo new db schema with snakecase columns + correct types
    private long gasUsed;
    @Column(name = "time_stamp")
    private Timestamp timestamp;

    @OneToMany(mappedBy = "block")
    private List<TransactionDm> transactions;

}
