package com.dbls.app.layer.message;

import akka.actor.ActorRef;
import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class UpFromRestartMessage implements Message {

    ActorRef author;

}
