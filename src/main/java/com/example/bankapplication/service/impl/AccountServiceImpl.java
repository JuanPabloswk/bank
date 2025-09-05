package com.example.bankapplication.service.impl;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.request.account.AccountOperationDTO;
import com.example.bankapplication.dto.request.account.AccountUpdateDTO;
import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.enums.AccountType;
import com.example.bankapplication.exception.*;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.model.Client;
import com.example.bankapplication.repository.AccountRepository;
import com.example.bankapplication.repository.ClientRepository;
import com.example.bankapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Override
    public Account createAccount(AccountCreateDTO accountCreateDTO) {
        String uniqueAccountNumber = "";

        do {

            String randomNumber = String.format("%08d", ThreadLocalRandom.current().nextInt(0, 100_000_000));

            if(accountCreateDTO.getAccountType() == AccountType.SAVING_ACCOUNT) {
                uniqueAccountNumber = "53" + randomNumber;
            }else if(accountCreateDTO.getAccountType() == AccountType.CHECKING_ACCOUNT){
                uniqueAccountNumber = "33" + randomNumber;
            }
        } while (accountRepository.existsByAccountNumber(uniqueAccountNumber));

        Client client = clientRepository.findByClientId(accountCreateDTO.getClientId())
                .orElseThrow(() -> new AccountNotFoundException("client not found"));

        Account account = accountMapper.createAccount(accountCreateDTO, uniqueAccountNumber);
        account.setClient(client);

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
            throw new NonZeroBalanceAccountDeletionException("You cannot eliminate accounts with a different balance to 0");
        }
    }

    @Override
    public void deposit(AccountOperationDTO accountOperationDTO) {
        Account account = accountRepository.findByAccountNumber(accountOperationDTO.getAccountNumber())
                .orElseThrow(() -> new ClientNotFoundException("Account not found"));


        if (account.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new CancelledAccountException("The account is cancelled and cannot process deposits.");
        }

        if (accountOperationDTO.getAmount() <= 0) {
            throw new InvalidAmountException("Amount must be greater than 0");
        }

        account.setBalance(account.getBalance() + accountOperationDTO.getAmount());
        accountRepository.save(account);
    }

    @Override
    public void withdraw(AccountOperationDTO accountOperationDTO) {
        Account account = accountRepository.findByAccountNumber(accountOperationDTO.getAccountNumber())
                .orElseThrow(() -> new AccountNotFoundException("Source account not found"));

        if (account.getAccountStatus() == AccountStatus.CANCELLED) {
            throw new CancelledAccountException("The account is cancelled and cannot process withdrawals.");
        }

        if (accountOperationDTO.getAmount() <= 0) {
            throw new InvalidAmountException("The withdrawal amount must be greater than 0.");
        }

        if (account.getBalance() < accountOperationDTO.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance.");
        }

        account.setBalance(account.getBalance() - accountOperationDTO.getAmount());
        accountRepository.save(account);
        }
}
