package com.pensasha.emoney.transaction;

import java.sql.Date;
import java.sql.Time;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.enums.Type;
import com.pensasha.emoney.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
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
public class Transaction {

    @Id
    @SequenceGenerator(name = "transaction_seq", sequenceName = "common_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transaction_seq")
    private Long id;

    @NotNull(message = "Date should be provided.")
    @PastOrPresent(message = "Date should be a date in the past or present.")
    private Date date;

    @NotNull(message = "Time should be provided.")
    @PastOrPresent(message = "Time should be in the past or present.")
    private Time time;

    @NotNull
    @Positive(message = "Amount should be greater than 0.")
    @Min(1)
    private int amount;

    @NotNull
    @Size(min = 2, max = 254, message = "Comment should have 2 to 254 characters.")
    @Column(length = 254)
    private String comment;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_idNumber")
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @NotNull
    private Account account;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @NotNull
    private Type type;

}
