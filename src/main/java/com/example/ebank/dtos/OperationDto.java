package com.example.ebank.dtos;

import com.example.ebank.entities.BankAccount;
import com.example.ebank.enums.OperationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
public class OperationDto {
    private Long id;
    private Date operationDate;
    private double amount;
    private String description;
    private OperationType type;
}
