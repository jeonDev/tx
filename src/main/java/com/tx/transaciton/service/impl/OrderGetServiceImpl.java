package com.tx.transaciton.service.impl;

import com.tx.transaciton.entity.Order;
import com.tx.transaciton.repo.OrderRepository;
import com.tx.transaciton.service.OrderGetService;
import com.tx.transaciton.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderGetServiceImpl implements OrderGetService {
    private final OrderRepository orderRepository;
    private final OrderService orderServiceImpl;

    @Transactional(readOnly = true)
    @Override
    public Order getOrder(Long id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        }

        Long createId = orderServiceImpl.create(10);
        return orderRepository.findById(createId)
                .orElseThrow();
    }
}
