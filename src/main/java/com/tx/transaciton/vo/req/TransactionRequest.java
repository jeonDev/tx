package com.tx.transaciton.vo.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TransactionRequest {

    private Long id;
    private boolean ok;
}
