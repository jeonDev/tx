package com.tx.transaciton.service.impl;

import com.tx.transaciton.entity.Transaction;
import com.tx.transaciton.repo.TransactionJpaRepository;
import com.tx.transaciton.service.TransactionService;
import com.tx.transaciton.type.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionJpaRepository transactionRepository;

    public TransactionServiceImpl(TransactionJpaRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    synchronized public Integer transaction() {
        log.info("transaction");
        Integer topCount = transactionRepository.findMaxCount();

        log.info("Top Count: {}", topCount);

        Transaction entity = Transaction.builder()
                .transactionType(TransactionType.REQUEST)
                .amount(BigDecimal.TEN)
                .count(topCount + 1)
                .build();
        entity = transactionRepository.save(entity);
        return entity.getCount();
    }
}
