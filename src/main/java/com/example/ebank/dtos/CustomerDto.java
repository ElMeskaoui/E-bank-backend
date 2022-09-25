package com.example.ebank.dtos;

import com.example.ebank.entities.BankAccount;
import com.example.ebank.entities.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
public class CustomerDto {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Collection<Role> roles;

}
