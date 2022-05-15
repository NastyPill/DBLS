package com.dbls.app.layer.subscriber.impl;

import com.dbls.app.layer.subscriber.WebSocketBlockchainSubscriber;
import org.web3j.protocol.websocket.events.LogNotification;

import java.net.ConnectException;
import java.util.Collections;
import java.util.function.Consumer;

public class SmartContractEventSubscriber extends WebSocketBlockchainSubscriber<LogNotification> {

    public SmartContractEventSubscriber() throws ConnectException {
    }

    @Override
    public void createSubscription(Consumer<LogNotification> consumer) {
        this.subscription = web3
                .logsNotifications(Collections.emptyList(), Collections.emptyList())
                .subscribe(consumer::accept, throwable -> {throw new Exception(throwable);});
                //, throwable -> {throw new SubscriptionFailureException(throwable);});
    }
}
