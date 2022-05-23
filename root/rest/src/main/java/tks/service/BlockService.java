package tks.service;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.model.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tks.model.Nm.BlockNm;
import tks.model.exceptions.TooWideTimeIntervalException;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
public class BlockService extends AbstractService {

    @Autowired
    RMQService rmqService;

    public AmqpResponse getBlockByHash(String hash) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.BLOCK_BY_HASH)
                .blockHash(hash)
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getBlockByMiner(String miner) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.BLOCK_BY_MINER)
                .miner(miner)
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public AmqpResponse getBlockByNumber(Long number) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.BLOCK_BY_NUMBER)
                .blockNumber(number.toString())
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }

    public List<BlockNm> getAll() {
        throw new UnsupportedOperationException();
    }

    public AmqpResponse getBlockByTimerange(String from, String to) throws InterruptedException {
        if(Timestamp.valueOf(to).getTime() - Timestamp.valueOf(from).getTime() >= 15 * 60 * 1000) {
            throw new TooWideTimeIntervalException();
        }
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.BLOCK_BY_TIMERANGE)
                .fromTime(Timestamp.valueOf(from))
                .toTime(Timestamp.valueOf(to))
                .build();
        return publishAndGet(amqpRequest, rmqService);
    }
}
