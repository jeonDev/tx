package com.tx.transaciton.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "ORDER_STOCK")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STOCK")
    private Integer stock;

    public void buy(Integer buyStock) {
        if (stock - buyStock < 0) {
            throw new RuntimeException("재고 없음");
        }
        this.stock = this.stock - buyStock;
    }
}
