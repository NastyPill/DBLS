package com.dbls.client.message;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewWorkResponse implements Message{

    AmqpRequest amqpRequest;
}
