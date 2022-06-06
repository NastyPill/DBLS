package com.dbls.client.message;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewWorkRequest implements Message {

    private AmqpResponse amqpResponse;

}
