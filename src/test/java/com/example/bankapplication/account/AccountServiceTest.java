package com.example.bankapplication.account;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.request.account.AccountOperationDTO;
import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.enums.AccountType;
import com.example.bankapplication.enums.ExemptGMF;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.mapper.TransactionMapper;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.model.Transaction;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.TransactionRepository;
import com.example.bankapplication.service.AccountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionMapper transactionMapper;

    @InjectMocks
    private AccountService accountService;

    @Test
    void createSavingAccount_valid_generatesValidAccountNumber() {
        AccountCreateDTO dto = new AccountCreateDTO(2L, ExemptGMF.EXEMPT, AccountType.SAVING_ACCOUNT);

        Account mappedAccount = new Account();
        mappedAccount.setAccountNumber("531234567890");

        when(accountMapper.createAccount(any(AccountCreateDTO.class), anyString()))
                .thenReturn(mappedAccount);
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);

        Account result = accountService.createAccount(dto);

        assertNotNull(result);
        assertTrue(result.getAccountNumber().startsWith("53"));
        assertEquals(12, result.getAccountNumber().length());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void deleteAccount_valid() {
        Long accountId = 2L;
        Account account = new Account();
        account.setBalance(0.0);
        account.setAccountStatus(AccountStatus.ACTIVE);

        when(accountRepository.findByAccountId(accountId)).thenReturn(Optional.of(account));

        accountService.deleteAccount(accountId);

        assertEquals(AccountStatus.CANCELLED, account.getAccountStatus());
        verify(accountRepository).save(account);
    }

    @Test
    void deleteAccount_invalid_balanceNotZero() {
        Long accountId = 2L;
        Account account = new Account();
        account.setBalance(100.0);

        when(accountRepository.findByAccountId(accountId)).thenReturn(Optional.of(account));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> accountService.deleteAccount(accountId));
        assertEquals("You shouldn't have money in the account", ex.getMessage());
    }

    @Test
    void withdraw_valid() {
        AccountOperationDTO dto = new AccountOperationDTO(50.0, "3312345678");
        Account account = new Account();
        account.setBalance(100.0);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountType(AccountType.CHECKING_ACCOUNT);

        when(accountRepository.findByAccountNumber(dto.getAccountNumber())).thenReturn(Optional.of(account));
        when(transactionMapper.makeTransfer(any(), any(), any())).thenReturn(new Transaction());

        accountService.withdraw(dto);

        assertEquals(50.0, account.getBalance());
        verify(transactionRepository).save(any());
    }

    @Test
    void withdraw_invalid_insufficientFunds() {
        AccountOperationDTO dto = new AccountOperationDTO(200.0, "3312345678");
        Account account = new Account();
        account.setBalance(100.0);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountType(AccountType.SAVING_ACCOUNT);

        when(accountRepository.findByAccountNumber(dto.getAccountNumber())).thenReturn(Optional.of(account));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(dto));
        assertEquals("Insufficient funds", ex.getMessage());
    }

    @Test
    void deposit_valid() {
        AccountOperationDTO dto = new AccountOperationDTO(200.0,"5312345678");
        Account account = new Account();
        account.setBalance(100.0);
        account.setAccountStatus(AccountStatus.ACTIVE);
        account.setAccountType(AccountType.SAVING_ACCOUNT);

        when(accountRepository.findByAccountNumber(dto.getAccountNumber())).thenReturn(Optional.of(account));
        when(transactionMapper.makeTransfer(any(), any(), any())).thenReturn(new Transaction());

        accountService.deposit(dto);

        assertEquals(300.0, account.getBalance());
        verify(transactionRepository).save(any());
    }

    @Test
    void deposit_invalid_inactiveAccount() {
        AccountOperationDTO dto = new AccountOperationDTO(100.0,"5312345678");
        Account account = new Account();
        account.setBalance(50.0);
        account.setAccountStatus(AccountStatus.CANCELLED);
        account.setAccountType(AccountType.SAVING_ACCOUNT);

        when(accountRepository.findByAccountNumber(dto.getAccountNumber())).thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> accountService.deposit(dto));
    }
}
