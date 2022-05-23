package com.dbls.app.layer.manager;

import com.dbls.app.layer.message.ListenerLayerMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.web3j.protocol.websocket.events.LogNotification;

@Data
@AllArgsConstructor
public class SmartContractLogMessage implements ListenerLayerMessage {
    private LogNotification log;

}
