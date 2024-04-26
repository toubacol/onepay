package com.decathlon.onepay.entity;

import com.decathlon.onepay.Util.PaymentTypeEnum;
import com.decathlon.onepay.Util.TransactionStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "PAYMENT_TRANSACTION")
@Getter
@Setter
public class Transaction {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_transaction_sequence_generator")
    @SequenceGenerator(name = "payment_transaction_sequence_generator", sequenceName = "seq_payment_transaction", allocationSize = 1)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "STATUS", columnDefinition = "varchar(255) default 'NEW'")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status = TransactionStatus.NEW;

    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private PaymentTypeEnum type;

    @Column(name = "AMOUNT")
    private BigDecimal amount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    private List<Order> orders;
}
