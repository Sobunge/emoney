package com.pensasha.emoney.houseStatus;

import java.sql.Date;

import com.pensasha.emoney.enums.House;
import com.pensasha.emoney.enums.Status;
import com.pensasha.emoney.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
    private Status status;
    private Date date;
    private House house;
    private User tenant;

}
