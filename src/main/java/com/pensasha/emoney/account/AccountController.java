package com.pensasha.emoney.account;

import java.security.Principal;
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

import com.pensasha.emoney.enums.Type;
import com.pensasha.emoney.transaction.Transaction;
import com.pensasha.emoney.transaction.TransactionService;
import com.pensasha.emoney.user.User;
import com.pensasha.emoney.user.UserService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class AccountController {

    private AccountService accountService;
    private UserService userService;
    private TransactionService transactionService;

    public AccountController(AccountService accountService, UserService userService,
            TransactionService transactionService) {
        this.accountService = accountService;
        this.userService = userService;
        this.transactionService = transactionService;
    }

     //Getting Account
    @GetMapping("/account/{id}")
    public String getAccount(Model model, @PathVariable Long id){

        List<User> allUsersNotInAccount = new ArrayList<>();
        List<User> allUsers = userService.getAllUsers();
        List<User> accountUsers = userService.getAccountUsers(id);

        for(User user : allUsers){
            if(accountUsers.contains(user)){
                continue;
            }else{
                allUsersNotInAccount.add(user);
            }
        }

        model.addAttribute("account", accountService.getAccount(id));
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("transactions");
        model.addAttribute("accountUsers", accountUsers);
        model.addAttribute("allUsersNotInAccount", allUsersNotInAccount);
        model.addAttribute("types", Type.values());
        model.addAttribute("accountTransactions", transactionService.getAllAccountTransaction(id));

        return "/accountsPages/account";
    }

    // Adding account
    @PostMapping("/account/create")
    public RedirectView createAccount(@ModelAttribute("account") Account account, Principal principal,
            RedirectAttributes redit) {

        if (accountService.doesAccountNameExist(account.getName())) {
            redit.addFlashAttribute("fail", "An account with name:" + account.getName() + " already exists.");
        } else {

            accountService.addAccount(account);

            redit.addFlashAttribute("success", account.getName() + " already exists.");
        }

        return new RedirectView("/accounts", true);
    }

    // Updating account details
    @PostMapping("/accounts/update")
    public RedirectView updateAccount(HttpServletRequest request, @ModelAttribute("account") Account account,
            RedirectAttributes redit) {

        Long id = Long.parseLong(request.getParameter("accountId"));

        if (accountService.doesAccountExistById(id)) {
            Account existingAccount = accountService.getAccount(id);

            existingAccount.setName(account.getName());
            existingAccount.setDescription(account.getDescription());

            accountService.updateAccount(existingAccount);
            redit.addFlashAttribute("success", "Account details updated successfully.");
        } else {
            redit.addFlashAttribute("fail", "Account with id:" + id + " does not exist.");
        }
        return new RedirectView("/accounts", true);

    }

    // Deleting account
    @GetMapping("/accounts/{id}")
    public RedirectView deleteAccount(@PathVariable Long id, RedirectAttributes redit, Principal principal) {

        if (accountService.doesAccountExistById(id)) {
            accountService.deleteAccount(id);
            redit.addFlashAttribute("success", "Account deleted successfully.");
        } else {
            redit.addFlashAttribute("fail", "Account does not exist.");
        }

        return new RedirectView("/accounts", true);
    }

    // Getting all accounts
    @GetMapping("/accounts")
    public String gettingAllAccounts(Model model, Principal principal) {

        List<Account> accounts = accountService.getAllAccounts();

        model.addAttribute("accounts", accounts);
        model.addAttribute("account", new Account());

        return "accountsPages/accounts";
    }

}
