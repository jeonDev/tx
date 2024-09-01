package com.tx.transaciton.service;

import com.tx.transaciton.vo.req.TransactionRequest;
import com.tx.transaciton.vo.res.TransactionResponse;

public interface TransactionService {

    TransactionResponse create();

    TransactionResponse transaction(TransactionRequest transactionRequest);
}
