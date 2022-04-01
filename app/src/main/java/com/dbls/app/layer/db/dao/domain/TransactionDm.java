package com.dbls.app.layer.db.dao.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "transaction", schema = "public")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class TransactionDm {

    @Id
    @SequenceGenerator(name = "transaction_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String hash;
    private long nonce;
    @Column(name = "transaction_index")
    private long transactionIndex;
    @Column(name = "from_")
    private String from;
    @Column(name = "to_")
    private String to;
    @Column(name = "value_")
    private long value;
    @Column(name = "gas_price")
    private long gasPrice;
    private long gas;
    @Column(name = "input_")
    private String input;
    private String creates;
    @Column(name = "public_key")
    private String publicKey;
    private String raw;
    private String r;
    private String s;
    private long v;

    @ManyToOne
    @JoinColumn(name="block_number")
    private BlockDm block;

}
