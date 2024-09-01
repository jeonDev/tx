package com.tx.transaciton.repo;

import com.tx.transaciton.entity.Transaction;
import com.tx.transaciton.type.TransactionType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class TransactionJpaRepository {
    private final TransactionRepository transactionRepository;

    public TransactionJpaRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public Transaction findById(Long id) {
        return transactionRepository.findByIdAndTransactionType(id, TransactionType.REQUEST)
                .orElseThrow(() -> new IllegalArgumentException("No Entity"));
    }

    @Transactional(readOnly = true)
    public Integer findMaxCount() {
        return transactionRepository.findTopCount()
                .orElse(0);
    }

    @Transactional
    public Transaction save(Transaction entity) {
        return transactionRepository.save(entity);
    }
}
