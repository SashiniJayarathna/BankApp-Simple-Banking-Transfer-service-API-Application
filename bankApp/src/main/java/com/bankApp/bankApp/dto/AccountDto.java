package com.bankApp.bankApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
    private Long id;
    private Long accountNumber;
    private String accountHolderName;
    private double balance;
}
