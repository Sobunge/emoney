package com.pensasha.emoney.tenant;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pensasha.emoney.enums.Role;
import com.pensasha.emoney.houseStatus.HouseStatus;
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
    @OneToMany(mappedBy = "tenants", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private List<HouseStatus> housesStatus;

    public Tenant(int idNumber, String firstName, String secondName, String thirdName, String nickname, int phoneNumber,
            String password, List<Role> roles) {
        super(idNumber, firstName, secondName, thirdName, nickname, phoneNumber, password, roles);
    }

}
