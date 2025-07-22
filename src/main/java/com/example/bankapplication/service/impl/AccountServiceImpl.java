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
    public void deposit(Long accountId, AccountOperationDTO accountOperationDTO) {
        Account account = accountRepository.findByAccountNumber(accountOperationDTO.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (accountOperationDTO.getAmount() < 0) {
            throw new RuntimeException("Amount must be greater than 0");
        }
        if (account.getAccountStatus() != AccountStatus.CANCELLED) {
            account.setBalance(account.getBalance() + accountOperationDTO.getAmount());

            accountRepository.save(account);
        }
    }

    @Override
    public void withdraw(Long accountId, AccountOperationDTO accountOperationDTO) {
        Account account = accountRepository.findByAccountNumber(accountOperationDTO.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Source account not found"));

        if (account.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new RuntimeException("The account is cancelled and cannot process withdrawals.");
        }

        if (accountOperationDTO.getAmount() <= 0) {
            throw new RuntimeException("The withdrawal amount must be greater than 0.");
        }

        if (account.getBalance() < accountOperationDTO.getAmount()) {
            throw new RuntimeException("Insufficient balance.");
        }

        account.setBalance(account.getBalance() - accountOperationDTO.getAmount());
        accountRepository.save(account);
        }
    }
