package com.example.ebank.dtos;

import lombok.Data;

@Data
public class AddOperationDto {
    private double amount;
    private String description;
}
