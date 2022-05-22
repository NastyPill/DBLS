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
import tks.model.Nm.BlockNm;
import tks.service.BlockService;

import java.util.List;

@RestController()
public class BlockController {

    private static Logger LOG = LoggerFactory.getLogger(BlockController.class);

    @Autowired
    BlockService blockService;

    @GetMapping("/api/block/getByHash")
    public ResponseEntity<String> getByHash(@RequestParam String hash) throws InterruptedException {
        return new ResponseEntity<>(blockService.getBlockByHash(hash).getBody(), HttpStatus.OK);
    }

    @GetMapping("/api/block/getByMiner")
    public ResponseEntity<List<BlockNm>> getByMiner(@RequestParam String miner) {
        return new ResponseEntity<>(blockService.getBlockByMiner(miner), HttpStatus.OK);
    }

    @GetMapping("/api/block/getByNumber")
    public ResponseEntity<BlockNm> getByNumber(@RequestParam Long number) {
        return new ResponseEntity<>(blockService.getBlockByNumber(number), HttpStatus.OK);
    }

    @GetMapping("/api/block/getAll")
    public ResponseEntity<List<BlockNm>> getAll() {
        return new ResponseEntity<>(blockService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/api/block/getByTimeRange")
    public ResponseEntity<List<BlockNm>> getByTimeRange(@ApiParam(example = "2022-04-01 19:21:36") @RequestParam String from,
                                                        @ApiParam(example = "2022-04-01 19:21:36") @RequestParam String to) {
        return new ResponseEntity<>(blockService.getBlockByTimerange(from, to), HttpStatus.OK);
    }
}