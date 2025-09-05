package com.example.bankapplication.transaction;

import com.example.bankapplication.dto.request.transaction.TransactionCreateDTO;
import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.mapper.TransactionMapper;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.model.Transaction;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.TransactionRepository;
import com.example.bankapplication.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock private AccountRepository accountRepository;
    @Mock private TransactionRepository transactionRepository;
    @Mock private TransactionMapper transactionMapper;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void transferMoney_valid() {
        TransactionCreateDTO dto = new TransactionCreateDTO(200.0, "5312345678", "3312345678");
        Account source = new Account();
        source.setBalance(100.0);
        source.setAccountStatus(AccountStatus.ACTIVE);
        Account destination = new Account();
        destination.setBalance(200.0);
        destination.setAccountStatus(AccountStatus.ACTIVE);

        when(accountRepository.findByAccountNumber(dto.getSourceAccountNumber())).thenReturn(Optional.of(source));
        when(accountRepository.findByAccountNumber(dto.getDestinationAccountNumber())).thenReturn(Optional.of(destination));
        when(transactionMapper.makeTransfer(any(), any(), any())).thenReturn(new Transaction());

        transactionService.transfer(dto);

        assertEquals(50.0, source.getBalance());
        assertEquals(250.0, destination.getBalance());
        verify(transactionRepository).save(any());
    }

    @Test
    void transferMoney_invalid_insufficientFunds() {
        TransactionCreateDTO dto = new TransactionCreateDTO(200.0, "5312345678", "3312345678");
        Account source = new Account();
        source.setBalance(100.0);
        source.setAccountStatus(AccountStatus.ACTIVE);
        Account to = new Account();
        to.setBalance(300.0);
        to.setAccountStatus(AccountStatus.ACTIVE);

        when(accountRepository.findByAccountNumber(dto.getSourceAccountNumber())).thenReturn(Optional.of(source));
        when(accountRepository.findByAccountNumber(dto.getDestinationAccountNumber())).thenReturn(Optional.of(to));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> transactionService.transfer(dto));
        assertEquals("Not enough money", ex.getMessage());
    }
}
