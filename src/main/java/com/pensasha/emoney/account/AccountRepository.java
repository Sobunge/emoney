package com.pensasha.emoney.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{

    public Boolean existsByName(String name);

    public List<Account> findAllByUsersIdNumber(int idNumber);
    
}
