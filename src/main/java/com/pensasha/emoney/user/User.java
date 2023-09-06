package com.pensasha.emoney.user;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.enums.Role;
import com.pensasha.emoney.transaction.Transaction;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
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
    @NotNull(message = "Enter your id number.")
    @Column(length = 8)
    @Min(100000)
    @Max(99999999)
    private int idNumber;

    @Column(length = 32)
    @NotNull
    @Size(min = 2, max = 32, message = "Must be between 2 and 32 characters.")
    private String firstName;

    @Column(length = 32)
    private String secondName;

    @Column(length = 32)
    @NotNull
    @Size(min = 2, max = 32, message = "Must be between 2 and 32 characters.")
    private String thirdName;

    @Column(length = 32)
    @NotNull
    @Size(min = 2, max = 32, message = "Must be between 2 and 32 characters.")
    private String nickname;

    @NotNull
    @Column(length = 9)
    @Min(100000000)
    @Max(799999999)
    private int phoneNumber;

    @NotNull
    @Size(min = 5, message = "Must be greater than 5 characters.")
    private String password;

    @ElementCollection(targetClass = Role.class)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "username"))
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role must be provided.")
    private List<Role> roles;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Account> accounts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<Transaction> transactions;

    public User(int idNumber, String firstName, String secondName, String thirdName, String nickname,
            int phoneNumber, String password, List<Role> roles) {
        this.idNumber = idNumber;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.roles = roles;
    }

}
