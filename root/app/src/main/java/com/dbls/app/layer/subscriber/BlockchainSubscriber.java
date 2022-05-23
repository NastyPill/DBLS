package com.dbls.app.layer.subscriber;

import org.reactivestreams.Subscription;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.protocol.websocket.WebSocketService;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public interface BlockchainSubscriber<T> {

    void createSubscription(Consumer<T> consumer);

    void onStop();

    void testAccesibility() throws ExecutionException, InterruptedException, TimeoutException;

}
