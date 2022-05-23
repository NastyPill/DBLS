package tks.service;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.model.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tks.model.Nm.TransactionNm;
import tks.model.exceptions.TooWideTimeIntervalException;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService extends AbstractService {

    @Autowired
    RMQService rmqService;

    public AmqpResponse getTransactionByHash(String hash) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.TRANSACTION_BY_HASH)
                .transactionHash(hash)
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getByBlockAndNumber(Long number, Long blockNumber) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.TRANSACTION_BY_NUMBER_AND_BLOCK)
                .blockNumber(blockNumber.toString())
                .transactionNumber(number.toString())
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getByBlockNumber(Long number) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.TRANSACTION_BY_BLOCK_NUMBER)
                .blockNumber(number.toString())
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getTransactionByTimestamp(String from, String to) throws InterruptedException {
        if(Timestamp.valueOf(to).getTime() - Timestamp.valueOf(from).getTime() >= 1 * 60 * 1000) {
            throw new TooWideTimeIntervalException();
        }
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.TRANSACTION_BY_TIMERANGE)
                .fromTime(Timestamp.valueOf(from))
                .toTime(Timestamp.valueOf(to))
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getByFrom(String from) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.TRANSACTION_BY_FROM)
                .from(from)
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }
}
