package tks.rest;

import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tks.model.Nm.TransactionNm;
import tks.service.TransactionService;

import java.util.List;

@RestController
public class TransactionController {

    private static Logger LOG = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    @GetMapping("/api/transaction/getByHash")
    public ResponseEntity<TransactionNm> getByHash(@RequestParam String hash) {
        return new ResponseEntity<>(transactionService.getTransactionByHash(hash), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getByBlock")
    public ResponseEntity<List<TransactionNm>> getByBlock(@RequestParam Long blockNumber) {
        return new ResponseEntity<>(transactionService.getByBlockNumber(blockNumber), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getByNumberAndBlock")
    public ResponseEntity<TransactionNm> getByNumber(@RequestParam Long number, @RequestParam Long blockNumber) {
        return new ResponseEntity<>(transactionService.getByBlockAndNumber(number, blockNumber), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getAll")
    public ResponseEntity<List<TransactionNm>> getAll() {
        return new ResponseEntity<>(transactionService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getByTimeRange")
    public ResponseEntity<List<TransactionNm>> getByTimeRange(@ApiParam(example = "2022-04-01 19:21:36") @RequestParam String from,
                                                              @ApiParam(example = "2022-04-01 19:21:36") @RequestParam String to) {
        return new ResponseEntity<>(transactionService.getTransactionByTimestamp(from, to), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getByFrom")
    public ResponseEntity<List<TransactionNm>> getByFrom(@RequestParam String from) {
        return new ResponseEntity<>(transactionService.getByFrom(from), HttpStatus.OK);
    }

}
