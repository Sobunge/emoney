package com.pensasha.emoney.transaction;

import java.security.Principal;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.account.AccountService;
import com.pensasha.emoney.enums.Type;
import com.pensasha.emoney.user.User;
import com.pensasha.emoney.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
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
    public RedirectView addingTransaction(@ModelAttribute Transaction transaction, @PathVariable Long id,
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

        return new RedirectView("/accounts/" + id + "/transactions", true);

    }

    // Getting a transaction
    @GetMapping("/accounts/{id}/transaction/{transactionId}")
    public String getTransaction(@PathVariable Long id, @PathVariable Long transactionId, Model model,
            Principal principal) {

        Account account = accountService.getAccount(id);
        List<Account> allAccounts = accountService.getAllAccounts();
        List<Account> accounts = new ArrayList<>();
        for (Account acc : allAccounts) {
            if (!acc.equals(account)) {
                accounts.add(acc);
            }
        }

        Transaction transaction = transactionService.getTransaction(transactionId);
        List<User> allAccountUsers = userService.getAccountUsers(id);
        List<User> accountUsers = new ArrayList<>();

        for (User user : allAccountUsers) {
            if (!user.equals(transaction.getUser())) {
                accountUsers.add(user);
            }
        }

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("account", account);
        model.addAttribute("accountUsers", accountUsers);
        model.addAttribute("transaction", transaction);
        model.addAttribute("types", Type.values());
        model.addAttribute("accounts", accounts);

        return "transactionPages/transaction";

    }

    // Updating transaction
    @PostMapping("/accounts/{id}/transaction/{transactionId}")
    public RedirectView updateTransaction(@Valid @ModelAttribute Transaction transaction, BindingResult bindingResult,
            @PathVariable Long id, @PathVariable Long transactionId,
            RedirectAttributes redit) {

        if (!accountService.doesAccountExistById(id)) {
            redit.addFlashAttribute("fail", "Account does not exist.");
        } else if (!transactionService.doesTransactionExist(transactionId)) {
            redit.addFlashAttribute("fail", "Transaction does not exist.");
        } else {

            Account newAccount = accountService.getAccount(transaction.getAccount().getId());
            List<User> newAccountUsers = userService.getAccountUsers(transaction.getAccount().getId());

            Transaction existingTransaction = transactionService.getTransaction(transactionId);
            Account currentAccount = accountService.getAccount(id);

            User user = userService.getUser(transaction.getUser().getIdNumber());
            List<Transaction> currentAccountTransactions = currentAccount.getTransactions();
            currentAccountTransactions.remove(existingTransaction);

            // Checking if user belongs to the new Account
            if (newAccountUsers.contains(user)) {

                // Checking if transaction user as the same
                if (!existingTransaction.getUser().equals(user)) {
                    existingTransaction.setUser(user);
                }

                existingTransaction.setComment(transaction.getComment());

                // Checking if the updated transaction has the same account to the exisiting
                // transaction
                if (currentAccount.getId().equals(newAccount.getId())) {

                    int sum = 0;

                    if (existingTransaction.getType().equals(Type.DEPOSIT)) {
                        sum = 10;
                    } else {
                        sum = 15;
                    }

                    if (existingTransaction.getType().equals(Type.DEPOSIT)) {
                        currentAccount.setBalance(currentAccount.getBalance() -
                                existingTransaction.getAmount());
                    } else {
                        currentAccount.setBalance(currentAccount.getBalance() +
                                existingTransaction.getAmount());
                    }

                    if (transaction.getType().equals(Type.DEPOSIT)) {
                        currentAccount.setBalance(currentAccount.getBalance() +
                                transaction.getAmount());
                    } else {
                        currentAccount.setBalance(currentAccount.getBalance() -
                                transaction.getAmount());
                    }

                    existingTransaction.setType(transaction.getType());
                    existingTransaction.setAccount(currentAccount);
                    existingTransaction.setAmount(transaction.getAmount());

                    currentAccountTransactions.add(existingTransaction);
                    currentAccount.setTransactions(currentAccountTransactions);

                } else {

                    if (existingTransaction.getType().equals(Type.DEPOSIT)) {
                        currentAccount.setBalance(currentAccount.getBalance() -
                                existingTransaction.getAmount());
                    } else {
                        currentAccount.setBalance(currentAccount.getBalance() +
                                existingTransaction.getAmount());
                    }

                    if (transaction.getType().equals(Type.DEPOSIT)) {
                        newAccount.setBalance(newAccount.getBalance() + transaction.getAmount());
                    } else {
                        newAccount.setBalance(newAccount.getBalance() - transaction.getAmount());
                    }

                    existingTransaction.setType(transaction.getType());
                    existingTransaction.setAccount(newAccount);
                    existingTransaction.setAmount(transaction.getAmount());
                    List<Transaction> newAccountTransactions = transactionService
                            .getAllAccountTransaction(newAccount.getId());
                    newAccountTransactions.add(existingTransaction);

                    currentAccount.setTransactions(currentAccountTransactions);
                    newAccount.setTransactions(newAccountTransactions);
                    accountService.updateAccount(newAccount);

                }

                accountService.updateAccount(currentAccount);

                redit.addFlashAttribute("success", "Transaction updated successfully.");

            } else {

                redit.addFlashAttribute("fail", user.getFirstName() + " " + user.getThirdName() + " does not belong to "
                        + newAccount.getName() + " Account.");
            }

        }

        return new RedirectView("/accounts/" + id + "/transaction/" + transactionId,
                true);

    }

    // Getting all transactions

    // Getting all account transactions
    @GetMapping("/accounts/{id}/transactions")
    public String getTransactions(@PathVariable Long id, Model model, Principal principal) {

        model.addAttribute("transaction", new Transaction());
        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("account", accountService.getAccount(id));
        model.addAttribute("accountTransactions", transactionService.getAllAccountTransaction(id));
        model.addAttribute("accountUsers", userService.getAccountUsers(id));
        model.addAttribute("types", Type.values());

        return "transactionPages/accountTransactions";
    }

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

        return "redirect:/chama/" + year + "/" + monthNo + "/" + month + "/" + daysInMonth;
    }

    @GetMapping("/chama/{year}/{monthNo}/{month}/{days}")
    public String getMonthlyChamaTrans(@PathVariable int year, @PathVariable int monthNo,
            @PathVariable String month,
            @PathVariable int days, Model model, Principal principal) throws ParseException {

        String startDate = year + "-" + monthNo + "-" + 01;
        String endDate = year + "-" + monthNo + "-" + days;

        Account account = accountService.getAccountByName("Chama");
        List<User> accountUsers = userService.getAccountUsers(account.getId());
        Map<Integer, List<Integer>> chamaTransaction = new HashMap<Integer, List<Integer>>();
        List<Transaction> transactions = transactionService.getAllTransactionBetweenDate(Date.valueOf(startDate),
                Date.valueOf(endDate));
        List<Integer> dailyTotals = new ArrayList<>();

        for (User user : accountUsers) {
            List<Integer> chama = new ArrayList<>();
            for (int i = 1; i <= days; i++) {
                int sum = 0;
                for (Transaction transaction : transactions) {
                    if ((transaction.getDate().equals(Date.valueOf(year + "-" + monthNo + "-" + i)))
                            && transaction.getUser().equals(user)) {
                        if (transaction.getType().equals(Type.DEPOSIT)) {
                            sum += transaction.getAmount();
                        } else {
                            sum -= transaction.getAmount();
                        }
                    }
                }
                chama.add(sum);
            }
            chamaTransaction.put(user.getIdNumber(), chama);
        }

        for (int i = 1; i <= days; i++) {
            int dailyTotal = 0;
            for (List<Integer> amount : chamaTransaction.values()) {
                dailyTotal += amount.get(i - 1);
            }

            dailyTotals.add(dailyTotal);
        }

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("account", account);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("daysInMonth", days);
        model.addAttribute("accountUsers", accountUsers);
        model.addAttribute("chamaTransaction", chamaTransaction);
        model.addAttribute("dailyTotals", dailyTotals);

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
