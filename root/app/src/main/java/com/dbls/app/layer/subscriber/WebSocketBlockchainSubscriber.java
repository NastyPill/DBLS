package com.dbls.app.layer.subscriber;

import io.reactivex.disposables.Disposable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class WebSocketBlockchainSubscriber<T> implements BlockchainSubscriber<T>{

    protected Web3j web3;
    protected Disposable subscription;

    private WebSocketService webSocketService;

    public WebSocketBlockchainSubscriber() throws ConnectException {
        if(web3 == null) {
            this.webSocketService = new WebSocketService("ws://192.168.0.6:9999", true);
            webSocketService.connect();
            this.web3 = Web3j.build(webSocketService);
        }
    }

    @Override
    public void onStop() {
        if(subscription != null)
            subscription.dispose();
        if(webSocketService != null)
            webSocketService.close();
    }

    @Override
    public void testAccesibility() throws ExecutionException, InterruptedException, TimeoutException {
        web3.ethBlockNumber().sendAsync().get(5, TimeUnit.SECONDS).getBlockNumber();
    }
}
