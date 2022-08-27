package com.example.ebank.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDto {
    private String accountId;
    private double balance;
    private String currency;
    private int currentPage;
    private int totalPages;
    private int pageSize;
    private List<OperationDto> operationDtoList;
}
