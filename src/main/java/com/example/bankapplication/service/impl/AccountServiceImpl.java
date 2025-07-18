package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.request.account.AccountOperationDTO;
import com.example.bankapplication.dto.request.account.AccountUpdateDTO;
import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.enums.AccountType;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    @Override
    public Account createAccount(AccountCreateDTO accountCreateDTO) {
        String randomNumber = String.format("%08d", ThreadLocalRandom.current().nextInt(0, 100_000_000));
        String uniqueAccountNumber = "";
        do {
            if(accountCreateDTO.getAccountType() == AccountType.SAVING_ACCOUNT) {
                uniqueAccountNumber = "53" + randomNumber;
            }else if(accountCreateDTO.getAccountType() == AccountType.CHECKING_ACCOUNT){
                uniqueAccountNumber = "33" + randomNumber;
            }
        } while (accountRepository.existsByAccountNumber(uniqueAccountNumber)) ;

        Account account = accountMapper.createAccount(accountCreateDTO, uniqueAccountNumber);
        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(Long accountId, AccountUpdateDTO accountUpdateDTO) {
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(RuntimeException::new);
        account.setAccountStatus(accountUpdateDTO.getAccountStatus());
        return accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long accountId){
        Account account = accountRepository.findByAccountId(accountId).orElseThrow(RuntimeException::new);
        if(account.getBalance() == 0){
            account.setAccountStatus(AccountStatus.CANCELLED);
            accountRepository.save(account);
        } else {
            throw new  RuntimeException("You cannot eliminate accounts with a different balance to 0");
        }
    }

    @Override
    public void withdraw(AccountOperationDTO accountOperationDTO) {
        Account account = accountRepository.findByAccountId(accountOperationDTO.getClientId()).orElseThrow(RuntimeException::new);
        if(account.getBalance() > 0 && account.getBalance() > accountOperationDTO.getAmount() && account.getAccountStatus() != AccountStatus.CANCELLED) {
            account.setBalance(account.getBalance() - accountOperationDTO.getAmount());
        }
    }

    @Override
    public void deposit(AccountOperationDTO accountOperationDTO) {
        Account account = accountRepository.findByAccountId(accountOperationDTO.getClientId()).orElseThrow(RuntimeException::new);
        if (accountOperationDTO.getAmount() > 0 && account.getAccountStatus() != AccountStatus.CANCELLED ) {
            account.setBalance(account.getBalance() + accountOperationDTO.getAmount());
        }
    }

    @Override
    public void transfer(AccountOperationDTO accountOperationDTO) {
        Account sourceAccount = accountRepository.findByAccountId(accountOperationDTO.getClientId()).orElseThrow(RuntimeException::new);
        Account destinationaccount = accountRepository.findByAccountId(accountOperationDTO.getClientId()).orElseThrow(RuntimeException::new);
        if(sourceAccount.getBalance() > 0 && accountOperationDTO.getAmount() > 0 && (sourceAccount.getAccountStatus() != AccountStatus.CANCELLED) || destinationaccount.getAccountStatus() != AccountStatus.CANCELLED) {
            sourceAccount.setBalance(sourceAccount.getBalance() - accountOperationDTO.getAmount());
        } else {
            throw new RuntimeException("Not enough balance");
        }
        if(accountOperationDTO.getAmount() > 0) {
            destinationaccount.setBalance(destinationaccount.getBalance() + accountOperationDTO.getAmount());
        } else  {
            throw new RuntimeException("The Amount's value is negative");
        }
    }
}