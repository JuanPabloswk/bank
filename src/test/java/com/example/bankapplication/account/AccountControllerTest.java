package com.example.bankapplication.account;

import com.example.bankapplication.controller.AccountController;
import com.example.bankapplication.dto.request.account.AccountCreateDTO;
import com.example.bankapplication.dto.request.account.AccountOperationDTO;
import com.example.bankapplication.dto.request.account.AccountUpdateDTO;
import com.example.bankapplication.dto.response.account.AccountResponseDTO;
import com.example.bankapplication.mapper.AccountMapper;
import com.example.bankapplication.model.Account;
import com.example.bankapplication.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class AccountControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @Mock
    private AccountMapper accountMapper;

    @InjectMocks
    private AccountController accountController;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        mockMvc = MockMvcBuilders.standaloneSetup(accountController).build();
    }

    @Test
    void createAccount_validInput_returnsCreatedAccount() throws Exception {
        AccountCreateDTO dto = new AccountCreateDTO(null, null, null);
        Account account = new Account();
        AccountResponseDTO responseDTO = new AccountResponseDTO();

        when(accountService.createAccount(any(AccountCreateDTO.class))).thenReturn(account);
        when(accountMapper.responseDTO(account)).thenReturn(responseDTO);

        mockMvc.perform(post("/api/accounts/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void deposit_validInput_returnsNoContent() throws Exception {
        AccountOperationDTO dto = new AccountOperationDTO(100.0, "123456789");

        doNothing().when(accountService).deposit(any(AccountOperationDTO.class));

        mockMvc.perform(post("/api/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void withdraw_validInput_returnsNoContent() throws Exception {
        AccountOperationDTO dto = new AccountOperationDTO(50.0, "123456789");

        doNothing().when(accountService).withdraw(any(AccountOperationDTO.class));

        mockMvc.perform(post("/api/accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateAccount_validInput_returnsUpdatedAccount() throws Exception {
        Long accountId = 1L;
        AccountUpdateDTO updateDTO = new AccountUpdateDTO(null, null);
        Account account = new Account();
        account.setAccountId(accountId);
        AccountResponseDTO responseDTO = new AccountResponseDTO();

        when(accountService.updateAccount(eq(accountId), any(AccountUpdateDTO.class))).thenReturn(account);
        when(accountMapper.responseDTO(account)).thenReturn(responseDTO);

        mockMvc.perform(put("/api/accounts/{accountId}", accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDTO)));
    }

    @Test
    void deleteAccount_validAccountId_returnsNoContent() throws Exception {
        Long accountId = 1L;

        doNothing().when(accountService).deleteAccount(accountId);

        mockMvc.perform(delete("/api/accounts/{accountId}", accountId))
                .andExpect(status().isNoContent());
    }
}
