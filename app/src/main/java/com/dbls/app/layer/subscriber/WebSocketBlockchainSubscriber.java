package com.dbls.app.layer.subscriber;

import io.reactivex.disposables.Disposable;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.websocket.WebSocketService;

import java.net.ConnectException;

public abstract class WebSocketBlockchainSubscriber<T> implements BlockchainSubscriber<T>{

    protected Web3j web3;
    protected Disposable subscription;

    private WebSocketService webSocketService;

    public WebSocketBlockchainSubscriber() {
        if(web3 == null) {
            this.webSocketService = new WebSocketService("ws://localhost:9999", true);
            try {
                webSocketService.connect();
            } catch (ConnectException e) {
                e.printStackTrace();
            }
            this.web3 = Web3j.build(webSocketService);
        }
    }

    @Override
    public void onStop() {
        subscription.dispose();
        webSocketService.close();
    }
}
