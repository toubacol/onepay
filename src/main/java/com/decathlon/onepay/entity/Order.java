package com.decathlon.onepay.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "PAYMENT_ORDER")
@Getter
@Setter
public class Order {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_order_sequence_generator")
    @SequenceGenerator(name = "payment_order_sequence_generator", sequenceName = "seq_payment_order", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @Column(name = "QUANTITY")
    private Integer quantity = 0;

    @Column(name = "PRICE")
    private BigDecimal price = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}
