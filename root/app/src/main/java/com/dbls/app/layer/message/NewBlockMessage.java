package com.dbls.app.layer.message;

import com.dbls.app.layer.message.ListenerLayerMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.web3j.protocol.core.methods.response.EthBlock;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBlockMessage implements ListenerLayerMessage {
    private EthBlock block;

}
