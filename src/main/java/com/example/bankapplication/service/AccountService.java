package com.example.bankapplication.service;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.request.account.AccountOperationDTO;
import com.example.bankapplication.dto.request.account.AccountUpdateDTO;
import com.example.bankapplication.model.Account;

public interface AccountService {

    Account createAccount(AccountCreateDTO accountCreateDTO);
    Account updateAccount(Long accountId, AccountUpdateDTO accountUpdateDTO);
    void deleteAccount(Long accountId);
    void deposit(AccountOperationDTO accountOperationDTO);
    void withdraw(AccountOperationDTO accountOperationDTO);
    void transfer(AccountOperationDTO accountOperationDTO);
}
