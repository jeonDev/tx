package com.tx.transaciton.service.impl;

import com.tx.transaciton.entity.Order;
import com.tx.transaciton.entity.OrderHistory;
import com.tx.transaciton.repo.OrderHistoryRepository;
import com.tx.transaciton.repo.OrderRepository;
import com.tx.transaciton.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderHistoryRepository orderHistoryRepository;

    @Transactional
    @Override
    public Long create(Integer stock) {
        Order order = orderRepository.save(Order.builder()
                .stock(stock)
                .build()
        );
        return order.getId();
    }

    @Transactional
    @Override
    public boolean buy(Long id) {
        log.info("id : {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No Entity"));
        log.info("order : {} {}", order.getId(), order.getStock());
        order.buy(1);

        log.info("order buy : {} {}", order.getId(), order.getStock());
        orderRepository.save(order);
        orderHistoryRepository.save(OrderHistory.builder()
                        .order(order)
                        .build()
        );
        return true;
    }
}
