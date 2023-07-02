package com.pensasha.emoney.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.transaction.Transaction;
import com.pensasha.emoney.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderColumn;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private int balance;

    @JsonIgnore
    @OrderColumn
    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(name = "users_accounts", joinColumns = @JoinColumn(name = "id"), 
    inverseJoinColumns = @JoinColumn(name = "idNumber"))
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = {CascadeType.ALL})
    private List<Transaction> transactions;

}
