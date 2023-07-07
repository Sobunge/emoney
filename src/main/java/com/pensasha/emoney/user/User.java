package com.pensasha.emoney.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.enums.Role;
import com.pensasha.emoney.transaction.Transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @NotNull(message = "You should enter your id number.")
    @Column(length = 8)
    @Min(1)
    private int idNumber;

    @Column(length = 32)
    @NotNull
    @Size(min = 2, max = 32, message = "Your first name should be between 2 and 32 characters.")
    private String firstName;

    @Column(length = 32)
    private String secondName;

    @Column(length = 32)
    @NotNull
    @Size(min = 2, max = 32, message = "Your third name should be between 2 and 32 characters.")
    private String thirdName;

    @NotNull
    @Column(length = 9)
    @Min(0100000000)
    private int phoneNumber;

    @NotNull
    @Size(min = 5, message = "Password should be greater than 5 characters.")
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role must be provided.")
    private Role role;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Account> accounts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Transaction> transactions;

    public User(int idNumber, String firstName, String secondName, String thirdName, int phoneNumber, String password,
            Role role) {
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
    }

}
