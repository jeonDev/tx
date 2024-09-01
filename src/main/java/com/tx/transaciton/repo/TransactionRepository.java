package com.tx.transaciton.repo;

import com.tx.transaciton.entity.Transaction;
import com.tx.transaciton.type.TransactionType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Transaction> findByIdAndTransactionType(Long id, TransactionType transactionType);

    @Query("SELECT MAX(t.count) FROM Transaction t")
    Optional<Integer> findTopCount();


}
