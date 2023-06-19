package com.pensasha.emoney.houseStatus;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class HouseStatusService {

    @Autowired
    private HouseStatusRepository houseStatusRepository;

    // Adding house status
    public HouseStatus addHouseStatus(HouseStatus houseStatus) {
        return houseStatusRepository.save(houseStatus);
    }

    // Updating house status
    public HouseStatus updateHouseStatus(HouseStatus houseStatus) {
        return houseStatusRepository.save(houseStatus);
    }

    // Getting all house status
    public List<HouseStatus> gettingAllHouseStatus() {
        return houseStatusRepository.findAll();
    }

    // Get house status
    public HouseStatus getHouseStatus(Long id) {
        return houseStatusRepository.findById(id).get();
    }

    // Deleting house status
    public void deleteHouseStatus(Long id) {
        houseStatusRepository.deleteById(id);
    }

}
