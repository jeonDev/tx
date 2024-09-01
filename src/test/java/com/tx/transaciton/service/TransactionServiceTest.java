package com.tx.transaciton.service;

import com.tx.transaciton.vo.req.TransactionRequest;
import com.tx.transaciton.vo.res.TransactionResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@DisplayName("Transaction Service")
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;
    private static final int THREAD_COUNT = 20;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("다중 거래 요청")
    void 다중_거래요청() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for(int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                TransactionResponse transactionResponse = transactionService.create();

                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest.setId(transactionResponse.getId());
                TransactionResponse transaction = transactionService.transaction(transactionRequest);

                latch.countDown();
            });
        }
        latch.await();
    }
}