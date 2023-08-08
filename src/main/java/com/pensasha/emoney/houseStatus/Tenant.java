package com.pensasha.emoney.houseStatus;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.user.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
    @OneToMany(mappedBy = "tenants", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<HouseStatus> housesStatus;

    
}
