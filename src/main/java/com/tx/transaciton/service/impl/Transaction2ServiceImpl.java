package com.tx.transaciton.service.impl;

import com.tx.transaciton.entity.Transaction2;
import com.tx.transaciton.repo.Transaction2Repository;
import com.tx.transaciton.service.TransactionService;
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
@Service("transactionService2")
public class Transaction2ServiceImpl implements TransactionService {

    private final Transaction2Repository transactionRepository;

    @Override
    public TransactionResponse create() {
        Transaction2 entity = transactionRepository.save(Transaction2.builder()
                .transactionType(TransactionType.REQUEST)
                .amount(BigDecimal.TEN)
                .build());
        return TransactionResponse.builder()
                .id(entity.getId())
                .build();
    }

    // TODO: @Transactional을 지우면 -> readonly 트랜잭션으로 수정 불가 ???
    @Transactional
    @Override
    public TransactionResponse transaction(TransactionRequest transactionRequest) {
        log.info("transaction : {}", transactionRequest.getId());
        Transaction2 entity = transactionRepository.findById(transactionRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("No Entity"));

        entity.complete();

        transactionRepository.save(entity);

        return TransactionResponse.builder()
                .id(entity.getId())
                .build();
    }
}
