package tks.service;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.service.AmqpConsumerService;
import com.dbls.client.service.AmqpPublisherService;
import com.dbls.client.service.configuration.RabbitMQConfiguration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

@Service
public class RMQService {

    private Map<String, AmqpResponse> responses;

    private AmqpConsumerService consumerService;
    private AmqpPublisherService publisherService;
    private ObjectMapper objectMapper;



    public RMQService() throws IOException, TimeoutException {
        this.responses = new ConcurrentHashMap<>();
        this.objectMapper = new ObjectMapper();
        //todo() read rmq config
        this.consumerService = new AmqpConsumerService(RabbitMQConfiguration.builder()
                .host("localhost")
                .port(5672)
                .consumableQueue("test.queue.out")
                .build());
        this.publisherService = new AmqpPublisherService(RabbitMQConfiguration.builder()
                .host("localhost")
                .port(5672)
                .publishingQueue("test.queue.in")
                .build());
        consumerService.consume(this::addToMap);
    }

    private void addToMap(String body) {
        try {
            AmqpResponse amqpResponse = objectMapper.readValue(body, AmqpResponse.class);
            responses.put(amqpResponse.getUuid(), amqpResponse);
        } catch (JsonProcessingException e) {
            //todo() handle exception
            e.printStackTrace();
        }
    }

    public AmqpResponse getResponse(String uuid) {
        AmqpResponse amqpResponse = responses.getOrDefault(uuid, null);
        if (amqpResponse != null) {
            responses.remove(uuid);
            return amqpResponse;
        }
        return null;
    }

    public void publishToQueue(AmqpRequest amqpRequest) {
        try {
            publisherService.publish(amqpRequest);
        } catch (IOException e) {
            //todo() handle exception
            e.printStackTrace();
        }
    }


}
