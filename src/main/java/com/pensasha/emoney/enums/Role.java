package com.pensasha.emoney.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Role {

    ADMIN("Admin"),
    LANDLORD("Landlord"),
    TENANT("Tenant"), 
    USER("User");

    public String role;
}
