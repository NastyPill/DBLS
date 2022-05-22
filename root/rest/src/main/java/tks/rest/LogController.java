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
    public ResponseEntity<List<LogNm>> getByBlockHash(@RequestParam String hash) {
        return new ResponseEntity<>(logService.getLogsByBlockHash(hash), HttpStatus.OK);
    }

    @GetMapping("/api/event/getByBlockNumber")
    public ResponseEntity<List<LogNm>> getByBlockNumber(@RequestParam Long number) {
        return new ResponseEntity<>(logService.getLogsByBlockNumber(number), HttpStatus.OK);
    }

    @GetMapping("/api/event/getByTransaction")
    public ResponseEntity<LogNm> getByTransactionHash(@RequestParam String hash) {
        return new ResponseEntity<>(logService.getLogsByTransactionHash(hash), HttpStatus.OK);
    }

    @GetMapping("/api/event/getByAddress")
    public ResponseEntity<List<LogNm>> getByAddress(@RequestParam String address) {
        return new ResponseEntity<>(logService.getLogsByAddress(address), HttpStatus.OK);
    }

}
