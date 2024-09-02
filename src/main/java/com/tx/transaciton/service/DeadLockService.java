package com.tx.transaciton.service;

import com.tx.transaciton.vo.req.TransactionRequest;
import com.tx.transaciton.vo.res.TransactionResponse;

public interface DeadLockService {

    TransactionResponse create(TransactionRequest request);

    TransactionResponse transaction(TransactionRequest request);
}
