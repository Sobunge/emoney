package com.pensasha.emoney.tenant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.enums.Residency;
import com.pensasha.emoney.houseStatus.HouseStatus;
import com.pensasha.emoney.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class Tenant extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "tenants", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<HouseStatus> housesStatus;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Residency must be provided.")
    private Residency residency;

}
