package tks.service;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.model.RequestType;
import tks.model.exceptions.EntityNotFoundException;

import java.util.UUID;

import static com.google.common.base.Strings.isNullOrEmpty;

public abstract class AbstractService {

    AmqpResponse publishAndGet(AmqpRequest amqpRequest, RMQService rmqService) throws InterruptedException {
        rmqService.publishToQueue(amqpRequest);
        AmqpResponse amqpResponse = null;
        while (amqpResponse == null) {
            amqpResponse = rmqService.getResponse(amqpRequest.getUuid());
            Thread.sleep(100L);
        }
        if(isNullOrEmpty(amqpResponse.getBody()) || amqpResponse.getBody().equals("[]")) {
            throw new EntityNotFoundException();
        }
        return amqpResponse;
    }

}
