package tks.model.Nm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TransactionNm {

    @JsonIgnore
    private long id;
    private String hash;
    private long nonce;
    private long transactionIndex;
    private String from;
    private String to;
    private long value;
    private long gasPrice;
    private long gas;
    private String input;
    private String creates;
    private String publicKey;
    private String raw;
    private String r;
    private String s;
    private long v;
}
