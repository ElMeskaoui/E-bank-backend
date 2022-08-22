package com.example.ebank.dtos;

import com.example.ebank.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CurrentBankAccountDto extends BankAccountDto{
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus Status;
    private String currency;
    private CustomerDto customerDto;
    private double overDraft;
}
