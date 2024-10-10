package com.tx.transaciton.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TxTestServiceTest {

    @Autowired
    private TxTestService txTest1ServiceImpl;

    @Test
    void tx테스트() {
        txTest1ServiceImpl.save();
    }
}