package com.pensasha.emoney.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    List<Transaction> findAllByAccountId(Long id);

    List<Transaction> findAllByUserIdNumber(int idNumber);

    List<Transaction> findAllByAccountIdAndUserIdNumber(Long id, Object idNumbe);
    
}
