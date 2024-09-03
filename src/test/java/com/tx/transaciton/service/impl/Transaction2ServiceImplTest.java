package com.tx.transaciton.service.impl;

import com.tx.transaciton.service.TransactionService;
import com.tx.transaciton.vo.req.TransactionRequest;
import com.tx.transaciton.vo.res.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class Transaction2ServiceImplTest {

    @Autowired
    private TransactionService transactionService2;

    private static final int THREAD_COUNT = 4;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("다중 거래 요청")
    void 다중_거래요청() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for(int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {
                TransactionResponse transactionResponse = transactionService2.create();

                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest.setId(transactionResponse.getId());
                if (transactionResponse.getId() % 2 == 0) {
                    transactionRequest.setId(transactionResponse.getId() - 1L);
                }
                TransactionResponse transaction = transactionService2.transaction(transactionRequest);
                log.info("End : {}", transaction.getId());
                latch.countDown();
            });
        }
        latch.await();
    }
}