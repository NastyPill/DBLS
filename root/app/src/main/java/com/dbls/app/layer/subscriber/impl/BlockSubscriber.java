package com.dbls.app.layer.subscriber.impl;

import com.dbls.app.layer.subscriber.WebSocketBlockchainSubscriber;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.net.ConnectException;
import java.util.function.Consumer;

public class BlockSubscriber extends WebSocketBlockchainSubscriber<EthBlock> {

    public BlockSubscriber() throws ConnectException {
    }

    @Override
    public void createSubscription(Consumer<EthBlock> consumer) {
        this.subscription = web3
                .blockFlowable(true)
                .subscribe(consumer::accept, throwable -> {throw new Exception(throwable);});
    //, throwable -> {throw new SubscriptionFailureException(throwable);});
    }
}
