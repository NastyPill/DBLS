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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String address;
    private String blockHash;
    private String blockNumber;
    private String data;
    private String logIndex;
    private String topics;
    private String transactionHash;
    private String transactionIndex;

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