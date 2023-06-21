package com.pensasha.emoney.houseStatus;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.enums.House;
import com.pensasha.emoney.enums.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Long id;
    private Date date;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_idNumber")
    private Tenant tenants;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private House house;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private Status status;
}
