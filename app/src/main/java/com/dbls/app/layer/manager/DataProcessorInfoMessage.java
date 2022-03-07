package com.dbls.app.layer.manager;

import akka.actor.typed.ActorRef;
import com.dbls.app.layer.message.DataProcessingLayerMessage;
import com.dbls.app.layer.message.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class DataProcessorInfoMessage implements DataProcessingLayerMessage {
    @NonNull
    ActorRef<Message> dataProcessor;
}
