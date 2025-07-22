package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.request.transaction.TransactionCreateDTO;
import com.example.bankapplication.enums.AccountStatus;
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
        Account sourceAccount = accountRepository.findByAccountId(transactionCreateDTO.getSourceAccountId())
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        Account destinationAccount = accountRepository.findByAccountId(transactionCreateDTO.getDestinationAccountId())
                .orElseThrow(() -> new RuntimeException("Destination account not found"));

        if (sourceAccount.getAccountStatus() == AccountStatus.CANCELLED ||
                destinationAccount.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new RuntimeException("One of the accounts is cancelled.");
        }

        if (transactionCreateDTO.getAmount() <= 0) {
            throw new RuntimeException("Transfer amount must be greater than 0.");
        }

        if (sourceAccount.getBalance() < transactionCreateDTO.getAmount()) {
            throw new RuntimeException("Insufficient balance in source account.");
        }

        sourceAccount.setBalance(sourceAccount.getBalance() - transactionCreateDTO.getAmount());
        destinationAccount.setBalance(destinationAccount.getBalance() + transactionCreateDTO.getAmount());

        Transaction transaction = transactionMapper.makeTransfer(transactionCreateDTO, sourceAccount, destinationAccount);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
        transactionRepository.save(transaction);
    }
}
