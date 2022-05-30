package com.dbls.app.layer.message;

import com.dbls.app.layer.message.ListenerLayerMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.protocol.websocket.events.LogNotification;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmartContractLogMessage implements ListenerLayerMessage {
    private LogNotification log;

}
