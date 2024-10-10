package com.tx.transaciton.service.impl;

import com.tx.transaciton.entity.TxTest2;
import com.tx.transaciton.repo.TxTest2Repository;
import com.tx.transaciton.service.TxTestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TxTest2ServiceImpl implements TxTestService {

    private final TxTest2Repository txTest2Repository;

    public TxTest2ServiceImpl(TxTest2Repository txTest2Repository) {
        this.txTest2Repository = txTest2Repository;
    }

    // 상위 트랜잭션 롤백 유무
    // 아무것도 없이 RuntimeException(); 발생 시, 정상 커밋 => AOP X
//    @Transactional(propagation = Propagation.REQUIRES_NEW)    // 이거 선언 시, no rollback => 새 트랜잭션만 rollback
//    @Transactional    // 이거 선언 시, rollback => Transaction AOP에서 RuntimeException 인식 -> 롤백
    @Override
    public void save() {
//        TxTest2 txTest2 = new TxTest2();
//        txTest2.setTestColumn(1L);
//        txTest2Repository.saveAndFlush(txTest2);
        throw new RuntimeException();
    }
}
