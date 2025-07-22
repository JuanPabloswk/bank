package com.example.bankapplication.controller;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.request.account.AccountOperationDTO;
import com.example.bankapplication.dto.request.account.AccountUpdateDTO;
import com.example.bankapplication.dto.response.account.AccountResponseDTO;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/create-account")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountCreateDTO accountCreateDTO) {
        Account savedAccount = accountService.createAccount(accountCreateDTO);
        AccountResponseDTO responseDTO = accountMapper.responseDTO(savedAccount);
        return ResponseEntity.ok().body(responseDTO);
    }

    @PostMapping("/{accountId}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long accountId, @RequestBody AccountOperationDTO accountOperationDTO) {
        accountService.deposit(accountId, accountOperationDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{accountId}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long accountId, @RequestBody AccountOperationDTO accountOperationDTO) {
        accountService.withdraw(accountId, accountOperationDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long accountId, @RequestBody AccountUpdateDTO accountUpdateDTO) {
        return ResponseEntity.ok().body(accountService.updateAccount(accountId, accountUpdateDTO));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }


}
