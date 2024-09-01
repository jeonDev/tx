package com.tx.transaciton.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private Long id;
    private Integer count;
}
