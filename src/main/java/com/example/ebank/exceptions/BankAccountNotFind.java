package com.example.ebank.exceptions;

import com.example.ebank.entities.BankAccount;

public class BankAccountNotFind extends Exception {
    public BankAccountNotFind(String message){
        super(message);
    }
}
