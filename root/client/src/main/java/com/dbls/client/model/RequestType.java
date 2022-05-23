package com.dbls.client.model;

public enum RequestType {
    BLOCK_BY_HASH(0),
    BLOCK_BY_MINER(1),
    BLOCK_BY_NUMBER(2),
    BLOCK_BY_TIMERANGE(3),
    LOG_BY_ADDRESS(4),
    LOG_BY_BLOCK_HASH(5),
    LOG_BY_BLOCK_NUMBER(6),
    LOG_BY_TRANSACTION_HASH(7),
    TRANSACTION_BY_BLOCK_NUMBER(8),
    TRANSACTION_BY_FROM(9),
    TRANSACTION_BY_HASH(10),
    TRANSACTION_BY_NUMBER_AND_BLOCK(11),
    TRANSACTION_BY_TIMERANGE(12);

    int value;

    RequestType(int value) {
        this.value = value;
    }

    public static RequestType fromValue(int value) {
        return RequestType.values()[value];
    }

    public int getValue() {
        return value;
    }
}
