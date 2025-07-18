package com.example.bankapplication.repository;

import com.example.bankapplication.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountId(Long accountId);
    Boolean existsByAccountNumber(String accountNumber);
}
