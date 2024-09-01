package com.tx.transaciton.service.impl;

import com.tx.transaciton.entity.Transaction;
import com.tx.transaciton.repo.TransactionRepository;
import com.tx.transaciton.service.TransactionService;
import com.tx.transaciton.type.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Override
    // row lock X
    synchronized public Integer transaction() {
        log.info("transaction");
        Integer topCount = transactionRepository.findTopCount()
                .orElse(0);

        log.info("Top Count: {}", topCount);

        Transaction entity = Transaction.builder()
                .transactionType(TransactionType.REQUEST)
                .amount(BigDecimal.TEN)
                .count(topCount + 1)
                .build();
        // TODO: commit 시점 전에 다음 메소드 호출
        entity = transactionRepository.saveAndFlush(entity);
        return entity.getCount();
    }
}
