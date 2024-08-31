package com.tx.transaciton.repo;

import com.tx.transaciton.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT MAX(t.count) FROM Transaction t")
    Optional<Integer> findTopCount();


}
