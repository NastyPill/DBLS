package tks.service;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.model.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tks.model.Nm.LogNm;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class LogService extends AbstractService {

    @Autowired
    RMQService rmqService;

    public AmqpResponse getLogsByBlockHash(String hash) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.LOG_BY_BLOCK_HASH)
                .blockHash(hash)
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getLogsByBlockNumber(Long number) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.LOG_BY_BLOCK_NUMBER)
                .blockNumber(number.toString())
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getLogsByTransactionHash(String hash) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.LOG_BY_TRANSACTION_HASH)
                .transactionHash(hash)
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getLogsByAddress(String address) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.LOG_BY_ADDRESS)
                .address(address)
                .build();
        return publishAndGet(amqpRequest, rmqService);    }
}
