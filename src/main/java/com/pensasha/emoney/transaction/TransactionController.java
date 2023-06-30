package com.pensasha.emoney.transaction;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.account.AccountService;
import com.pensasha.emoney.enums.Type;
import com.pensasha.emoney.user.User;
import com.pensasha.emoney.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class TransactionController {
    
    private TransactionService transactionService;
    private UserService userService;
    private AccountService accountService;

    public TransactionController(TransactionService transactionService, UserService userService,
            AccountService accountService) {
        this.transactionService = transactionService;
        this.userService = userService;
        this.accountService = accountService;
    }

    //Adding a transaction
    @PostMapping("/accounts/{id}/transaction")
    public RedirectView getTransaction(@ModelAttribute("transaction") Transaction transaction,@PathVariable Long id,
     Model model, RedirectAttributes redit, HttpServletRequest request){
        
        Account account = accountService.getAccount(id);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        User user = userService.getUser(Integer.parseInt(request.getParameter("userSelect")));
        
        transaction.setDate(Date.valueOf(date));
        transaction.setTime(Time.valueOf(time));
        transaction.setUser(user);
        transaction.setAccount(accountService.getAccount(id));    

        if(transaction.getType().equals(Type.DEPOSIT)){
            account.setBalance(account.getBalance() + transaction.getAmount());
        }else{
            account.setBalance(account.getBalance() - transaction.getAmount());
        }

        accountService.updateAccount(account);
        transactionService.addingTransaction(transaction);
        
        redit.addFlashAttribute("success", "Transaction successfully added.");
        
        return new RedirectView("/account/{id}", true);
    }

    //Updating a transaction

    //Getting a transaction

    //Getting all transactions

    //Getting all account transactions

    //Deleting transaction


}
