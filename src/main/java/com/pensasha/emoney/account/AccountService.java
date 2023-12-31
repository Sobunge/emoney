package com.pensasha.emoney.account;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Add account
    public Account addAccount(Account account) {
        return accountRepository.save(account);
    }

    // Update account details
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    // Delete account
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    // Getting all accounts
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    // Getting all accounts by user
    public List<Account> getAllAccountsByIdNumber(int idNumber) {
        return accountRepository.findAllByUsersIdNumber(idNumber);
    }

    // Get account
    public Account getAccount(Long id) {
        return accountRepository.findById(id).get();
    }

    // Getting account by name
    public Account getAccountByName(String name){
        return accountRepository.findByName(name);
    }

    // Checking if account exist by id
    public Boolean doesAccountExistById(Long id) {
        return accountRepository.existsById(id);
    }

    // Checking if an account name exist
    public Boolean doesAccountNameExist(String name) {
        return accountRepository.existsByName(name);
    }

}
