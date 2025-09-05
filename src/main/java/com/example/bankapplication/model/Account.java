package com.example.bankapplication.model;

import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.enums.AccountType;
import com.example.bankapplication.enums.ExemptGMF;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id", nullable = false)
    private Long accountId;


    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false)
    private AccountType accountType;

    @Column(name = "account_number",  nullable = false)
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private double balance = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "exempt_gmf", nullable = false)
    private ExemptGMF exemptGMF;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDate updatedAt;



    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
