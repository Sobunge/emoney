package com.pensasha.emoney.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {

    USER("User"),
    ADMIN("Admin"), 
    TENANT("Tenant"),
    LANDLORD("Landlord");

    public String role;
}
