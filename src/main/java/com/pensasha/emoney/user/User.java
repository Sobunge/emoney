package com.pensasha.emoney.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class User {

    @Id
    private int idNumber;
    private String firstName;
    private String secondName;
    private String thirdName;
    private int phoneNumber;
    private String password;

}
