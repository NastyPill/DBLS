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
    public ResponseEntity<String> getByHash(@RequestParam String hash) throws InterruptedException {
        return new ResponseEntity<>(transactionService.getTransactionByHash(hash).getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getByBlock")
    public ResponseEntity<String> getByBlock(@RequestParam Long blockNumber) throws InterruptedException {
        return new ResponseEntity<>(transactionService.getByBlockNumber(blockNumber).getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getByNumberAndBlock")
    public ResponseEntity<String> getByNumber(@RequestParam Long number, @RequestParam Long blockNumber) throws InterruptedException {
        return new ResponseEntity<>(transactionService.getByBlockAndNumber(number, blockNumber).getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getByTimeRange")
    public ResponseEntity<String> getByTimeRange(@ApiParam(example = "2022-04-01 19:21:36") @RequestParam String from,
                                                              @ApiParam(example = "2022-04-01 19:21:36") @RequestParam String to) throws InterruptedException {
        return new ResponseEntity<>(transactionService.getTransactionByTimestamp(from, to).getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/transaction/getByFrom")
    public ResponseEntity<String> getByFrom(@RequestParam String from) throws InterruptedException {
        return new ResponseEntity<>(transactionService.getByFrom(from).getBody(), HttpStatus.OK);
    }

}
