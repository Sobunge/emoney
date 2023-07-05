package com.pensasha.emoney.transaction;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    // Adding a transaction
    @PostMapping("/accounts/{id}/transaction")
    public RedirectView getTransaction(@ModelAttribute Transaction transaction, @PathVariable Long id,
            Model model, RedirectAttributes redit, HttpServletRequest request) {

        List<Transaction> transactions = new ArrayList<>();
        Account account = accountService.getAccount(id);
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        User user = userService.getUser(Integer.parseInt(request.getParameter("userSelect")));
        transactions.addAll(account.getTransactions());

        transaction.setDate(Date.valueOf(date));
        transaction.setTime(Time.valueOf(time));
        transaction.setUser(user);

        if (transaction.getType().equals(Type.DEPOSIT)) {
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else {
            account.setBalance(account.getBalance() - transaction.getAmount());
        }

        transaction.setAccount(account);
        transactions.add(transaction);

        account.setTransactions(transactions);

        accountService.addAccount(account);

        redit.addFlashAttribute("success", "Transaction successfully added.");

        return new RedirectView("/account/" + id, true);

    }

    // Getting a transaction

    // Getting all transactions

    // Getting all account transactions

    // Deleting transaction
    @GetMapping("/accounts/{id}/transactions/{transactionId}")
    public RedirectView deleteTransactionFromAccount(@PathVariable Long id, @PathVariable Long transactionId,
            RedirectAttributes redit) {

        if (accountService.doesAccountExistById(id)) {
            if (transactionService.doesTransactionExist(transactionId)) {

                Transaction transaction = transactionService.getTransaction(transactionId);
                Account account = accountService.getAccount(id);

                if (transaction.getType().equals(Type.DEPOSIT)) {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                } else {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                }

                accountService.updateAccount(account);

                transactionService.deleteTransaction(transactionId);

                redit.addFlashAttribute("success", "Transaction deleted successfully.");

            } else {
                redit.addFlashAttribute("fail", "Transaction does not exist.");
            }
        } else {
            redit.addFlashAttribute("fail", "Account does not exist.");
        }

        return new RedirectView("/account/" + id, true);
    }

}
