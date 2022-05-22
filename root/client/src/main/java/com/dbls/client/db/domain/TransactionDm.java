package com.dbls.client.db.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    private long id;
    @JsonProperty
    private String hash;
    @JsonProperty
    private long nonce;
    @Column(name = "transaction_index")
    @JsonProperty
    private long transactionIndex;
    @Column(name = "from_")
    @JsonProperty
    private String from;
    @Column(name = "to_")
    @JsonProperty
    private String to;
    @Column(name = "value_")
    @JsonProperty
    private long value;
    @Column(name = "gas_price")
    @JsonProperty
    private long gasPrice;
    @JsonProperty
    private long gas;
    @Column(name = "input_")
    @JsonProperty
    private String input;
    @JsonProperty
    private String creates;
    @Column(name = "public_key")
    @JsonProperty
    private String publicKey;
    @JsonProperty
    private String raw;
    @JsonProperty
    private String r;
    @JsonProperty
    private String s;
    @JsonProperty
    private long v;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="block_number")
    private BlockDm block;

}
