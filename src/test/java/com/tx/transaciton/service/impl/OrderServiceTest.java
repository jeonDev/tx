package com.tx.transaciton.service.impl;

import com.tx.transaciton.repo.OrderRepository;
import com.tx.transaciton.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Order Service")
class OrderServiceTest {
    @Autowired
    private OrderService orderServiceImpl;
    @Autowired
    private OrderRepository orderRepository;
    private static final int THREAD_COUNT = 5;
    private static Long id;

    @Test
    @Transactional
    @Rollback(value = false)
    @Order(1)
    @DisplayName("상품 생성")
    void 상품_생성() {
        id = orderServiceImpl.create(5);
        assertNotNull(id);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @Order(2)
    @DisplayName("다중 거래 요청")
    void 다중_거래_요청() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        for(int i = 0; i < THREAD_COUNT; i++) {
            executorService.execute(() -> {

                orderServiceImpl.buy(id);
                latch.countDown();
            });
        }
        latch.await();

        com.tx.transaciton.entity.Order order = orderRepository.findById(id).get();
        assertNotNull(order);
        assertEquals(order.getStock(), 0);
    }
}