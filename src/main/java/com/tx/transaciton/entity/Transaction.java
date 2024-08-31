package com.tx.transaciton.entity;

import com.tx.transaciton.type.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "COUNT")
    private Integer count;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_TYPE")
    private TransactionType transactionType;

    @Column(name = "AMOUNT")
    private BigDecimal amount;
}
