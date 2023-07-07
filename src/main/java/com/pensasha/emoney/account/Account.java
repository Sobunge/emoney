package com.pensasha.emoney.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.transaction.Transaction;
import com.pensasha.emoney.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @SequenceGenerator(name = "account_seq", sequenceName = "common_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
    private Long id;

    @NotNull
    @Size(min = 2, max = 32, message = "Name should be between 2 and 32 characters.")
    @Column(length = 32)
    private String name;

    @NotNull
    @Size(min = 5, max = 254, message = "Account description characters should be between 5 and 254 characters.")
    @Column(length = 254)
    private String description;

    @NotNull
    @Min(0)
    private int balance;

    @JsonIgnore
    @OrderColumn
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "users_accounts", joinColumns = @JoinColumn(name = "id"), inverseJoinColumns = @JoinColumn(name = "idNumber"))
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    public Account(String name, String description) {
        this.name = name;
        this.description = description;
        this.balance = 0;
    }

}
