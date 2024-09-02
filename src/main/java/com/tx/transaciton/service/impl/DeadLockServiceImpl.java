package com.tx.transaciton.service.impl;

import com.tx.transaciton.entity.Transaction;
import com.tx.transaciton.repo.TransactionRepository;
import com.tx.transaciton.service.DeadLockService;
import com.tx.transaciton.type.TransactionType;
import com.tx.transaciton.vo.req.TransactionRequest;
import com.tx.transaciton.vo.res.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeadLockServiceImpl implements DeadLockService {

    private final TransactionRepository transactionRepository;

    @Transactional
    @Override
    public TransactionResponse create(TransactionRequest request) {
        log.info("Create : {}", request.getId());
        Transaction entity = transactionRepository.findByIdAndTransactionType(request.getId(), TransactionType.REQUEST)
                .orElse(Transaction.builder()
                        .id(request.getId())
                        .transactionType(TransactionType.REQUEST)
                        .count(0)
                        .amount(BigDecimal.TEN)
                        .build());

        entity = transactionRepository.save(entity);

        return TransactionResponse.builder()
                .id(entity.getId())
                .build();
    }

    @Transactional
    @Override
    public TransactionResponse transaction(TransactionRequest transactionRequest) {
        log.info("Transaction : {}", transactionRequest.getId());

        Transaction entity = transactionRepository.findByIdAndTransactionType(transactionRequest.getId(), TransactionType.REQUEST)
                .orElseThrow(() -> new IllegalArgumentException("No Entity"));

        entity.complete();
        return TransactionResponse.builder()
                .id(entity.getId())
                .build();
    }
}
