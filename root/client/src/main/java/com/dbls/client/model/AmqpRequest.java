package com.dbls.client.model;

import com.sun.istack.Nullable;
import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AmqpRequest {

    @NonNull
    String uuid;
    @NonNull
    RequestType type;
    @Nullable
    String transactionHash;
    @Nullable
    String transactionNumber;
    @Nullable
    String blockHash;
    @Nullable
    String blockNumber;
    @Nullable
    String miner;
    @Nullable
    String address;
    @Nullable
    String from;
    @Nullable
    Timestamp fromTime;
    @Nullable
    Timestamp toTime;



}
