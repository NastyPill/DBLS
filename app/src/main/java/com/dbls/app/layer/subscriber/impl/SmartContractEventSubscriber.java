package com.dbls.app.layer.subscriber.impl;

import com.dbls.app.layer.subscriber.WebSocketBlockchainSubscriber;
import org.web3j.protocol.websocket.events.LogNotification;

import java.util.Collections;
import java.util.function.Consumer;

public class SmartContractEventSubscriber extends WebSocketBlockchainSubscriber<LogNotification> {

    @Override
    public void createSubscription(Consumer<LogNotification> consumer) {
        this.subscription = web3.logsNotifications(Collections.emptyList(), Collections.emptyList()).subscribe(consumer::accept);
    }
}
