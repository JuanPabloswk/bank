package com.example.bankapplication.controller;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.request.account.AccountUpdateDTO;
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

    @PostMapping("/create-account")
    public ResponseEntity<Account> createAccount(@RequestBody AccountCreateDTO accountCreateDTO) {
        Account account = accountService.createAccount(accountCreateDTO);
        return ResponseEntity.ok().body(account);
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
