package com.tx.transaciton.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
@NoArgsConstructor
@Table(name = "TX_TEST1")
public class TxTest1 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
