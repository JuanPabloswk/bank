package com.example.bankapplication.mapper;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.response.account.AccountResponseDTO;
import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.model.Account;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountMapper {

    public Account createAccount(AccountCreateDTO accountCreateDTO, String uniqueAccountNumber) {
    Account account = new Account();
    account.setAccountType(accountCreateDTO.getAccountType());
    account.setAccountNumber(uniqueAccountNumber);
    account.setExemptGMF(accountCreateDTO.getExemptGMF());
    account.setAccountStatus(AccountStatus.ACTIVE);
    account.setCreatedAt(LocalDateTime.now());
    return account;
    }

    public AccountResponseDTO responseDTO(Account account) {
        AccountResponseDTO accountResponseDTO = new AccountResponseDTO();
        accountResponseDTO.setBalance(account.getBalance());
        accountResponseDTO.setAccountNumber(account.getAccountNumber());
        return accountResponseDTO;
    }
}
