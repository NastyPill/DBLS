package com.dbls.app.layer.db.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "transaction", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String hash;
    private String nonce;
    @Column(name = "transactionIndex")
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    @Column(name = "gasPrice")
    private String gasPrice;
    private String gas;
    private String input;
    private String creates;
    @Column(name = "publicKey")
    private String publicKey;
    private String raw;
    private String r;
    private String s;
    private long v;

    @ManyToOne
    @JoinColumn(name="number", nullable = false)
    private BlockDm block;

}
