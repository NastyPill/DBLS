package tks.service;

import org.springframework.stereotype.Service;
import tks.model.Nm.LogNm;

import java.util.List;

@Service
public class LogService {

    public List<LogNm> getLogsByBlockHash(String hash) {
        throw new UnsupportedOperationException();
    }

    public List<LogNm> getLogsByBlockNumber(Long number) {
        throw new UnsupportedOperationException();
    }

    public LogNm getLogsByTransactionHash(String hash) {
        throw new UnsupportedOperationException();
    }

    public List<LogNm> getLogsByAddress(String address) {
        throw new UnsupportedOperationException();
    }
}
