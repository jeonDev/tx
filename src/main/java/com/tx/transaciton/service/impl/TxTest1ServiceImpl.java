package com.tx.transaciton.service.impl;

import com.tx.transaciton.entity.TxTest1;
import com.tx.transaciton.repo.TxTest1Repository;
import com.tx.transaciton.service.TxTestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class TxTest1ServiceImpl implements TxTestService {

    private final TxTestService txTestService;
    private final TxTest1Repository txTest1Repository;

    public TxTest1ServiceImpl(TxTestService txTest2ServiceImpl,
                              TxTest1Repository txTest1Repository) {
        this.txTestService = txTest2ServiceImpl;
        this.txTest1Repository = txTest1Repository;
    }

    @Transactional
    @Override
    public void save() {
        TxTest1 txTest1 = new TxTest1();
        txTest1Repository.save(txTest1);

        try {
            txTestService.save();
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
        }
    }
}
