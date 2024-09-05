package com.tx.transaciton.service.impl;

import com.tx.transaciton.service.OrderService;
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

@SpringBootTest
@DisplayName("Order Service")
class OrderServiceTest {
    @Autowired
    private OrderService orderServiceImpl;
    private static final int THREAD_COUNT = 5;

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("다중 거래 요청")
    void 다중_거래_요청() throws InterruptedException {
        Long id = orderServiceImpl.create(5);
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

        for(int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {

                orderServiceImpl.buy(id);
                latch.countDown();
            });
        }
        latch.await();
    }
}