package com.example.bankapplication.controller;

import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.request.account.AccountOperationDTO;
import com.example.bankapplication.dto.request.account.AccountUpdateDTO;
import com.example.bankapplication.dto.response.account.AccountResponseDTO;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(accountMapper.responseDTO(accountService.createAccount(dto)));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Void> deposit(@RequestBody AccountOperationDTO accountOperationDTO) {
        accountService.deposit(accountOperationDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Void> withdraw(@RequestBody AccountOperationDTO accountOperationDTO) {
        accountService.withdraw(accountOperationDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{accountId}")
    public ResponseEntity<AccountResponseDTO> updateAccount(
            @PathVariable Long accountId, @RequestBody AccountUpdateDTO dto) {
        return ResponseEntity.ok(accountMapper.responseDTO(accountService.updateAccount(accountId, dto)));
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }


}
