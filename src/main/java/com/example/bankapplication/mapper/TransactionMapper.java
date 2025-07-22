package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.request.transaction.TransactionCreateDTO;
import com.example.bankapplication.enums.OperationType;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    public Transaction makeTransfer(TransactionCreateDTO dto, Account source, Account destination) {
        Transaction transaction = new Transaction();
        transaction.setOperationType(OperationType.TRANSFER);
        transaction.setAmount(dto.getAmount());
        transaction.setSourceAccount(source);
        transaction.setDestinationAccount(destination);
        transaction.setTransactionDate(LocalDateTime.now());
        return transaction;
    }
}

