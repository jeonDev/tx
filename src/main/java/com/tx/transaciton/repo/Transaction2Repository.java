package com.tx.transaciton.repo;

import com.tx.transaciton.entity.Transaction2;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface Transaction2Repository extends JpaRepository<Transaction2, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Transaction2> findById(Long id);
}
