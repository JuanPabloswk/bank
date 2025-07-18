package com.example.bankapplication.model;

import com.example.bankapplication.enums.OperationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "transactions")
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private OperationType operationType;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;



    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Client client;
}
