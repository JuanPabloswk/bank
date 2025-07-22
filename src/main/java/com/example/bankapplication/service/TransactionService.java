package com.example.bankapplication.service;

import com.example.bankapplication.dto.request.transaction.TransactionCreateDTO;

public interface TransactionService {

    void transfer(TransactionCreateDTO transactionCreateDTO);
}
