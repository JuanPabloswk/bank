package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.request.transaction.TransactionCreateDTO;
import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.exception.CancelledAccountException;
import com.example.bankapplication.exception.ClientNotFoundException;
import com.example.bankapplication.exception.InvalidAmountException;
import com.example.bankapplication.mapper.TransactionMapper;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.model.Transaction;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.TransactionRepository;
import com.example.bankapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Override
    public void transfer(TransactionCreateDTO transactionCreateDTO) {
        Account sourceAccount = accountRepository.findByAccountNumber(transactionCreateDTO.getSourceAccountNumber())
                .orElseThrow(() -> new ClientNotFoundException("Source account not found"));

        Account destinationAccount = accountRepository.findByAccountNumber(transactionCreateDTO.getDestinationAccountNumber())
                .orElseThrow(() -> new ClientNotFoundException("Destination account not found"));

        if (sourceAccount.getAccountStatus() == AccountStatus.CANCELLED ||
                destinationAccount.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new CancelledAccountException("One of the accounts is cancelled.");
        }

        if (transactionCreateDTO.getAmount() <= 0) {
            throw new InvalidAmountException("Transfer amount must be greater than 0.");
        }

        if (sourceAccount.getBalance() < transactionCreateDTO.getAmount()) {
            throw new InvalidAmountException("Insufficient balance in source account.");
        }

        // actualizar saldos
        sourceAccount.setBalance(sourceAccount.getBalance() - transactionCreateDTO.getAmount());
        destinationAccount.setBalance(destinationAccount.getBalance() + transactionCreateDTO.getAmount());

        // crear transacción
        Transaction transaction = transactionMapper.makeTransfer(transactionCreateDTO, sourceAccount, destinationAccount);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
        transactionRepository.save(transaction);
    }
}

