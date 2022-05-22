package tks.service;

import org.springframework.stereotype.Service;
import tks.model.Nm.BlockNm;

import java.util.List;

@Service
public class BlockService {

    public BlockNm getBlockByHash(String hash) {
        throw new UnsupportedOperationException();
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
