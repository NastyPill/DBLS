package com.dbls.app.layer.db.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDm {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String hash;
    private String nonce;
    private String transactionIndex;
    private String from;
    private String to;
    private String value;
    private String gasPrice;
    private String gas;
    private String input;
    private String creates;
    private String publicKey;
    private String raw;
    private String r;
    private String s;
    private long v;

    @ManyToOne
    @JoinColumn(name="number", nullable = false)
    private BlockDm block;

}
