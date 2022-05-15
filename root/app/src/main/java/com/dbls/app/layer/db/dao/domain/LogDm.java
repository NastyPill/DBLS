package com.dbls.app.layer.db.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "log")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDm {

    @Id
    @SequenceGenerator(name = "log_id_seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String address;
    @Column(name = "block_hash")
    private String blockHash;
    @Column(name = "block_number")
    private long blockNumber;
    private String data;
    @Column(name = "log_index")
    private long logIndex;
    private String topics;
    @Column(name = "transaction_hash")
    private String transactionHash;
    @Column(name = "transaction_index")
    private long transactionIndex;

    public void setTopics(List<String> topics) {
        StringBuilder result = new StringBuilder();
        for (String topic: topics) {
            result.append(topic);
            result.append(" ;; ");
        }
        this.topics = result.toString();
    }

    public List<String> getTopics() {
        return Arrays
                .stream(topics
                        .split(" ;; "))
                .collect(Collectors.toList());
    }
}