package tks.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tks.model.Nm.LogNm;
import tks.service.LogService;

import java.util.List;

@RestController
public class LogController {

    private static Logger LOG = LoggerFactory.getLogger(LogController.class);

    @Autowired
    LogService logService;

    @GetMapping("/api/event/getByBlock")
    public ResponseEntity<String> getByBlockHash(@RequestParam String hash) throws InterruptedException {
        return new ResponseEntity<>(logService.getLogsByBlockHash(hash).getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/event/getByBlockNumber")
    public ResponseEntity<String> getByBlockNumber(@RequestParam Long number) throws InterruptedException {
        return new ResponseEntity<>(logService.getLogsByBlockNumber(number).getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/event/getByTransaction")
    public ResponseEntity<String> getByTransactionHash(@RequestParam String hash) throws InterruptedException {
        return new ResponseEntity<>(logService.getLogsByTransactionHash(hash).getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/event/getByAddress")
    public ResponseEntity<String> getByAddress(@RequestParam String address) throws InterruptedException {
        return new ResponseEntity<>(logService.getLogsByAddress(address).getBody(), HttpStatus.OK);
    }

}
