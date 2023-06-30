package com.pensasha.emoney.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{

    List<User> findAllByAccountsId(Long accountId);

    Boolean existsByIdNumberAndAccountsId(int idNumber, Long id);
    
}
