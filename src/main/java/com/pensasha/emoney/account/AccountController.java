package com.pensasha.emoney.account;

import java.security.Principal;
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


@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    // Adding account
    @PostMapping("/account/create")
    public RedirectView createAccount(@ModelAttribute("account") Account account, Principal principal,
            RedirectAttributes redit) {

        if (accountService.doesAccountNameExist(account.getName())) {
            redit.addFlashAttribute("fail", "An account with name:" + account.getName() + " already exists.");
        } else {

            accountService.addAccount(account);

            redit.addAttribute("success", account.getName() + " already exists.");
        }

        return new RedirectView("/accounts", true);
    }

    // Updating account details
    @PostMapping("/accounts/{id}/update")
    public RedirectView updateAccount(@PathVariable Long id, @ModelAttribute("account") Account account,
            RedirectAttributes redit) {

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
    public String gettingAllAccounts(Model model, Principal principal){

        List<Account> accounts = accountService.getAllAccounts();
        model.addAttribute("accounts", accounts);

        return "accountsPages/accounts";
    }

}
