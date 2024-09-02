package com.tx.transaciton.service;

import com.tx.transaciton.vo.req.TransactionRequest;
import com.tx.transaciton.vo.res.TransactionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@DisplayName("DeadLock Service")
public class DeadLockServiceTest {

    @Autowired
    private DeadLockService deadLockService;
    private static final int THREAD_COUNT = 5;

    @Test
    @DisplayName("다중 요청")
    void 다중_요청() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for(int i = 0; i < THREAD_COUNT; i++) {
            int finalI = i;
            executorService.execute(() -> {
                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest.setId((long) finalI);
                TransactionResponse transactionResponse = deadLockService.create(transactionRequest);

                // DeadLock
//                TransactionResponse transaction = deadLockService.transaction(transactionRequest);

                latch.countDown();
            });
        }
        latch.await();
    }
/***
 * 2024-09-02T21:11:07.249+09:00  WARN 4060 --- [pool-2-thread-4] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1213, SQLState: 40001
 * 2024-09-02T21:11:07.249+09:00  WARN 4060 --- [pool-2-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1213, SQLState: 40001
 * 2024-09-02T21:11:07.249+09:00  WARN 4060 --- [pool-2-thread-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1213, SQLState: 40001
 * 2024-09-02T21:11:07.250+09:00  WARN 4060 --- [pool-2-thread-5] o.h.engine.jdbc.spi.SqlExceptionHelper   : SQL Error: 1213, SQLState: 40001
 * 2024-09-02T21:11:07.250+09:00 ERROR 4060 --- [pool-2-thread-4] o.h.engine.jdbc.spi.SqlExceptionHelper   : Deadlock found when trying to get lock; try restarting transaction
 * 2024-09-02T21:11:07.250+09:00 ERROR 4060 --- [pool-2-thread-5] o.h.engine.jdbc.spi.SqlExceptionHelper   : Deadlock found when trying to get lock; try restarting transaction
 * 2024-09-02T21:11:07.250+09:00 ERROR 4060 --- [pool-2-thread-2] o.h.engine.jdbc.spi.SqlExceptionHelper   : Deadlock found when trying to get lock; try restarting transaction
 * 2024-09-02T21:11:07.250+09:00 ERROR 4060 --- [pool-2-thread-1] o.h.engine.jdbc.spi.SqlExceptionHelper   : Deadlock found when trying to get lock; try restarting transaction
 * 2024-09-02T21:11:07.251+09:00 TRACE 4060 --- [pool-2-thread-3] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save]
 * 2024-09-02T21:11:07.251+09:00 TRACE 4060 --- [pool-2-thread-3] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.tx.transaciton.service.impl.DeadLockServiceImpl.create]
 * 2024-09-02T21:11:07.251+09:00 TRACE 4060 --- [pool-2-thread-5] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save] after exception: jakarta.persistence.OptimisticLockException: org.hibernate.exception.LockAcquisitionException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 * 2024-09-02T21:11:07.251+09:00 TRACE 4060 --- [pool-2-thread-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save] after exception: jakarta.persistence.OptimisticLockException: org.hibernate.exception.LockAcquisitionException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 * 2024-09-02T21:11:07.252+09:00 TRACE 4060 --- [pool-2-thread-2] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save] after exception: jakarta.persistence.OptimisticLockException: org.hibernate.exception.LockAcquisitionException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 * 2024-09-02T21:11:07.251+09:00 TRACE 4060 --- [pool-2-thread-4] o.s.t.i.TransactionInterceptor           : Completing transaction for [org.springframework.data.jpa.repository.support.SimpleJpaRepository.save] after exception: jakarta.persistence.OptimisticLockException: org.hibernate.exception.LockAcquisitionException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 * 2024-09-02T21:11:07.253+09:00 TRACE 4060 --- [pool-2-thread-1] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.tx.transaciton.service.impl.DeadLockServiceImpl.create] after exception: org.springframework.dao.CannotAcquireLockException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]; SQL [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 * 2024-09-02T21:11:07.253+09:00 TRACE 4060 --- [pool-2-thread-2] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.tx.transaciton.service.impl.DeadLockServiceImpl.create] after exception: org.springframework.dao.CannotAcquireLockException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]; SQL [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 * 2024-09-02T21:11:07.253+09:00 TRACE 4060 --- [pool-2-thread-4] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.tx.transaciton.service.impl.DeadLockServiceImpl.create] after exception: org.springframework.dao.CannotAcquireLockException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]; SQL [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 * 2024-09-02T21:11:07.253+09:00 TRACE 4060 --- [pool-2-thread-5] o.s.t.i.TransactionInterceptor           : Completing transaction for [com.tx.transaciton.service.impl.DeadLockServiceImpl.create] after exception: org.springframework.dao.CannotAcquireLockException: could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]; SQL [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 SQL Error: 1213, SQLState: 40001
 Deadlock found when trying to get lock; try restarting transaction
 could not execute statement [Deadlock found when trying to get lock; try restarting transaction] [insert into transaction (amount,count,transaction_type) values (?,?,?)]
 Caused by: com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException: Deadlock found when trying to get lock; try restarting transaction
 */
}
