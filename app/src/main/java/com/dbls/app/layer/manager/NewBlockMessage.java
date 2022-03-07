package com.dbls.app.layer.manager;

import com.dbls.app.layer.message.ListenerLayerMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.web3j.protocol.core.methods.response.EthBlock;

@Data
@AllArgsConstructor
public class NewBlockMessage implements ListenerLayerMessage {
    private EthBlock block;

}
