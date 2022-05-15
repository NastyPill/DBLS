package com.dbls.client.model;

import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;

@Builder
@Getter
public class AmqpRequest {

    RequestType type;
    String transactionHash;
    String transactionNumber;
    String blockHash;
    String blockNumber;
    String miner;
    String address;
    String from;
    Timestamp fromTime;
    Timestamp toTime;



}
