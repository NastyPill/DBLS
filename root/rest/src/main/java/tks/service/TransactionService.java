package tks.service;

import org.springframework.stereotype.Service;
import tks.model.Nm.TransactionNm;

import java.util.List;

@Service
public class TransactionService {

    public TransactionNm getTransactionByHash(String hash) {
        throw new UnsupportedOperationException();
    }

    public TransactionNm getByBlockAndNumber(Long number, Long blockNumber) {
        throw new UnsupportedOperationException();
    }

    public List<TransactionNm> getAll() {
        throw new UnsupportedOperationException();
    }

    public List<TransactionNm> getByBlockNumber(Long number) {
        throw new UnsupportedOperationException();
    }

    public List<TransactionNm> getTransactionByTimestamp(String from, String to) {
        throw new UnsupportedOperationException();
    }

    public List<TransactionNm> getByFrom(String from) {
        throw new UnsupportedOperationException();
    }
}
