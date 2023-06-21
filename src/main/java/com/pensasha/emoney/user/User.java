package com.pensasha.emoney.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.enums.Role;
import com.pensasha.emoney.transaction.Transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {

    @Id
    private int idNumber;
    private String firstName;
    private String secondName;
    private String thirdName;
    private int phoneNumber;
    private String password;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Account> accounts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Transaction> transactions;

}
