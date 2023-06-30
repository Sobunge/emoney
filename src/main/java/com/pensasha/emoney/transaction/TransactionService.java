package com.pensasha.emoney.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Adding a transaction
    public Transaction addingTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Updating transaction details
    public Transaction updatingTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // Getting all transactions
    public List<Transaction> gettingAllTransaction() {
        return transactionRepository.findAll();
    }

    //Getting all account transactions
    public List<Transaction> getAllAccountTransaction(Long id){
        return transactionRepository.findAllByAccountId(id);
    }

    // Getting a transaction
    public Transaction getTransaction(Long id) {
        return transactionRepository.findById(id).get();
    }

    // Deleting a transaction
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }

}
