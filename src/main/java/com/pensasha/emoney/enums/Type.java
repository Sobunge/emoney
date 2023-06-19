package com.pensasha.emoney.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Type {

    DEPOSIT("Deposit"),
    WITHDRAWAL("Withdrawal");

    public String type;
    
}
