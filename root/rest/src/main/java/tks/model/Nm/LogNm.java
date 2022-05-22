package tks.model.Nm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class LogNm {

    @JsonIgnore
    private long id;
    private String address;
    private String blockHash;
    private long blockNumber;
    private String data;
    private long logIndex;
    private List<String> topics;
    private String transactionHash;
    private long transactionIndex;
}
