package com.dbls.app.layer.subscriber.impl;

import com.dbls.app.layer.subscriber.WebSocketBlockchainSubscriber;
import io.reactivex.disposables.Disposable;
import org.web3j.protocol.core.methods.response.EthBlock;

import java.util.function.Consumer;

public class BlockSubscriber extends WebSocketBlockchainSubscriber<EthBlock> {

    @Override
    public void createSubscription(Consumer<EthBlock> consumer) {
        this.subscription = web3.blockFlowable(true).subscribe(consumer::accept, throwable -> {throw new Exception(throwable);});
    }
}
