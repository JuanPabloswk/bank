package com.example.bankapplication.dto.request.account;

import com.example.bankapplication.enums.AccountStatus;
import com.example.bankapplication.enums.ExemptGMF;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountUpdateDTO {

    private ExemptGMF exemptGMF;
    private AccountStatus accountStatus;
}
