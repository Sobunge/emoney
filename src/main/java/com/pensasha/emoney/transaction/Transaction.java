package com.pensasha.emoney.transaction;

import java.sql.Date;
import java.sql.Time;

import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.enums.Type;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    
    @Id
    private Long id;
    private String name;
    private Date date;
    private Time time;
    private int amount;
    private Type type;
    private String comment;
    private Account account;

}
