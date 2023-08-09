package com.pensasha.emoney.houseStatus;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.enums.House;
import com.pensasha.emoney.enums.Status;
import com.pensasha.emoney.tenant.Tenant;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseStatus {

    @Id
    @SequenceGenerator(name = "houseStatus_seq", sequenceName = "houseStatus_sequence", initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "houseStatus_seq")
    private Long id;

    @NotNull
    private Date date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_idNumber")
    @NotNull
    private Tenant tenants;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @NotNull
    private House house;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;
}
