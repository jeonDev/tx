package com.tx.transaciton.service;

public interface OrderService {

    Long create(Integer stock);
    boolean buy(Long id);
}
