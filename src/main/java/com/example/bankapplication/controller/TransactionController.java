package com.example.bankapplication.controller;

import com.example.bankapplication.dto.request.transaction.TransactionCreateDTO;
import com.example.bankapplication.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> transfer(@RequestBody TransactionCreateDTO transactionCreateDTO) {
        transactionService.transfer(transactionCreateDTO);
        return ResponseEntity.ok("Transfer successful");
    }
}