package tks.service;

import com.dbls.client.model.AmqpRequest;
import com.dbls.client.model.AmqpResponse;
import com.dbls.client.model.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tks.model.Nm.BlockNm;

import java.util.List;
import java.util.UUID;

@Service
public class BlockService {

    @Autowired
    RMQService rmqService;

    public AmqpResponse getBlockByHash(String hash) throws InterruptedException {
        String uuid = UUID.randomUUID().toString();
        AmqpRequest amqpRequest = AmqpRequest.builder()
                .uuid(uuid)
                .type(RequestType.BLOCK_BY_HASH)
                .blockHash(hash)
                .build();
        rmqService.publishToQueue(amqpRequest);
        AmqpResponse amqpResponse = null;
        while (amqpResponse == null) {
            amqpResponse = rmqService.getResponse(uuid);
            Thread.sleep(100L);
        }
        return amqpResponse;
    }

    public List<BlockNm> getBlockByMiner(String miner) {
        throw new UnsupportedOperationException();
    }

    public BlockNm getBlockByNumber(Long number) {
        throw new UnsupportedOperationException();
    }

    public List<BlockNm> getAll() {
        throw new UnsupportedOperationException();
    }

    public List<BlockNm> getBlockByTimerange(String from, String to) {
        throw new UnsupportedOperationException();
    }
}
