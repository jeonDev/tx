package com.tx.transaciton.service.impl;

import com.tx.transaciton.service.OrderGetService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.JpaSystemException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("트랜잭션 전파")
class OrderGetServiceTest {

    @Autowired
    private OrderGetService orderGetServiceImpl;

    @Test
    @DisplayName("상위 트랜잭션 읽기전용 테스트")
    void 상위_트랜잭션_읽기전용_테스트() {
        // 테스트 시, Lock 제거 (제거 안할 시, 하위 트랜잭션에서 걸리는게 아니라 첫 메소드에서 Lock 걸 때, readOnly 문제)
        assertThrows(JpaSystemException.class,
                () -> orderGetServiceImpl.getOrder(100L)
        );
    }
}
