package com.pensasha.emoney.transaction;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

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
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Controller
@AllArgsConstructor
public class TransactionController {

    private TransactionService transactionService;
    private UserService userService;
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

    // Gettting monthly chama transactions
    @PostMapping("/accounts/chama")
    public String getMonthlyChamaTransaction(HttpServletRequest request) {

        String month = request.getParameter("monthInput");
        int monthNo = 0;
        int daysInMonth = 0;
        YearMonth yearMonth;
        int year = Integer.parseInt(request.getParameter("yearInput"));

        switch (month) {
            case "January":
                yearMonth = YearMonth.of(year, 1);
                monthNo = 1;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "February":
                yearMonth = YearMonth.of(year, 2);
                monthNo = 2;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "March":
                yearMonth = YearMonth.of(year, 3);
                monthNo = 3;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "April":
                yearMonth = YearMonth.of(year, 4);
                monthNo = 4;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "May":
                yearMonth = YearMonth.of(year, 5);
                monthNo = 5;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "June":
                yearMonth = YearMonth.of(year, 6);
                monthNo = 6;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "July":
                yearMonth = YearMonth.of(year, 7);
                monthNo = 7;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "August":
                yearMonth = YearMonth.of(year, 8);
                monthNo = 8;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "September":
                yearMonth = YearMonth.of(year, 9);
                monthNo = 9;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "October":
                yearMonth = YearMonth.of(year, 10);
                monthNo = 10;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "November":
                yearMonth = YearMonth.of(year, 11);
                monthNo = 11;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            case "December":
                yearMonth = YearMonth.of(year, 12);
                monthNo = 12;
                daysInMonth = yearMonth.lengthOfMonth();
                break;
            default:
                break;
        }

        return "redirect:/chama/" + year + "/" + monthNo + "-" + month + "/" + daysInMonth;
    }

    @GetMapping("/chama/{year}/{monthNo}-{month}/{days}")
    public String getMonthlyChamaTrans(@PathVariable int year, @PathVariable int monthNo, @PathVariable String month,
            @PathVariable int days, Model model) throws ParseException {

        String startDate = year + "-" + monthNo + "-" + 01;
        String endDate = year + "-" + monthNo + "-" + days;

        Account account = accountService.getAccountByName("Chama");
        List<User> accountUsers = userService.getAccountUsers(account.getId());
        List<Transaction> transactions = transactionService.getAllTransactionBetweenDate(Date.valueOf(startDate),
                Date.valueOf(endDate));

        model.addAttribute("account", account);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("daysInMonth", days);
        model.addAttribute("accountUsers", accountUsers);

        return "transactionPages/chama";
    }

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
