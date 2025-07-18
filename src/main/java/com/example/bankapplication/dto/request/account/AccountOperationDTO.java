package com.example.bankapplication.dto.request.account;

import com.example.bankapplication.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountOperationDTO {

    private double amount;
    private Long clientId;

    private OperationType operationType;
}
