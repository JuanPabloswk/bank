package com.example.bankapplication.dto.request.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreateDTO {

    private double amount;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
}
