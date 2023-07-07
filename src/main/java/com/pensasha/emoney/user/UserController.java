package com.pensasha.emoney.user;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.account.AccountService;
import com.pensasha.emoney.enums.Role;
import com.pensasha.emoney.enums.Type;
import com.pensasha.emoney.transaction.Transaction;
import com.pensasha.emoney.transaction.TransactionService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    // Pages

    // Adding a user Get Request
    @GetMapping("/users/register")
<<<<<<< HEAD
    public String registration(Model model, Principal principal, @ModelAttribute User newUser) {
=======
    public String registration(User newUser, Model model, Principal principal) {
>>>>>>> 98be9908c2b79219dbf5a5a28254bfea25b3d029

        model.addAttribute("activeUser",
                userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("roles", Role.values());
        model.addAttribute("newUser", newUser);

        return "usersPages/registration";

    }

    // Adding a user Post Request
    @PostMapping("/users/register")
<<<<<<< HEAD
    public RedirectView postRegistration(@Valid User newUser, RedirectAttributes redit, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new RedirectView("/users/register", true);
=======
    public String postRegistration(@Valid @ModelAttribute("newUser") User newUser, BindingResult bindingResult,
            Model model, RedirectAttributes redit) {

        if (bindingResult.hasErrors()) {

            redit.addAttribute("newUser", newUser);
            return "redirect:/users/register";
>>>>>>> 98be9908c2b79219dbf5a5a28254bfea25b3d029
        } else {

            if (userService.doesUserExist(newUser.getIdNumber())) {
                redit.addFlashAttribute("newUser", newUser);
<<<<<<< HEAD
                redit.addFlashAttribute("fail", "User with Username:" + newUser.getIdNumber() + " already exists.");

                return new RedirectView("/users/register", true);
=======
                redit.addFlashAttribute("fail", "User with Username:" + newUser.getIdNumber()
                        + " already exists.");

                return "redirect:/users/register";
>>>>>>> 98be9908c2b79219dbf5a5a28254bfea25b3d029
            } else {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                newUser.setPassword(encoder.encode(newUser.getPassword()));
                userService.addUser(newUser);
                redit.addFlashAttribute("success", "User was successfully added.");

<<<<<<< HEAD
                return new RedirectView("/users", true);
=======
                return "redirect:/users";
>>>>>>> 98be9908c2b79219dbf5a5a28254bfea25b3d029
            }

        }

    }

    // Getting all users
    @GetMapping("/users")
    public String returnAllUsers(Principal principal, Model model) {

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("users", userService.getAllUsers());

        return "usersPages/users";
    }

    // Deleting a user
    @GetMapping("/users/{idNumber}")
    public RedirectView deleteUser(@PathVariable int idNumber, RedirectAttributes redit) {
        if (userService.doesUserExist(idNumber)) {

            User user = userService.getUser(idNumber);

            List<Account> accounts = accountService.getAllAccountsByIdNumber(idNumber);
            for (Account account : accounts) {
                account.getUsers().remove(user);
                List<Transaction> transactions = transactionService.getAllTransactionForAccountByUser(account.getId(),
                        idNumber);
                for (Transaction transaction : transactions) {
                    if (transaction.getType().equals(Type.DEPOSIT)) {
                        account.setBalance(account.getBalance() - transaction.getAmount());
                    } else {
                        account.setBalance(account.getBalance() + transaction.getAmount());
                    }
                    transactionService.deleteTransaction(transaction.getId());
                }
                accountService.updateAccount(account);
            }

            userService.deleteUserDetails(idNumber);
            redit.addFlashAttribute("success", "User successfully deleted");
        } else {
            redit.addFlashAttribute("fail", "User with id:" + idNumber + " does not exist.");
        }
        return new RedirectView("/users", true);
    }

    // Deleting user from account
    @GetMapping("/accounts/{id}/users/{idNumber}")
    public RedirectView deleteUserFromAccount(@PathVariable Long id, @PathVariable int idNumber,
            RedirectAttributes redit) {
        if (userService.doesUserExistInAccount(idNumber, id)) {
            User user = userService.getUser(idNumber);
            Account account = accountService.getAccount(id);
            List<Transaction> transactions = transactionService.getAllTransactionForAccountByUser(id, idNumber);
            for (Transaction transaction : transactions) {
                if (transaction.getType().equals(Type.DEPOSIT)) {
                    account.setBalance(account.getBalance() - transaction.getAmount());
                } else {
                    account.setBalance(account.getBalance() + transaction.getAmount());
                }
                transactionService.deleteTransaction(transaction.getId());
            }
            account.getUsers().remove(user);
            accountService.updateAccount(account);

            redit.addFlashAttribute("success",
                    user.getFirstName() + ' ' + user.getThirdName() + " successfully removed from account.");
        } else {
            redit.addFlashAttribute("fail", "User does not exist in account.");
        }

        return new RedirectView("/account/" + id, true);
    }

    // Getting a single user
    @GetMapping("/user/profile/{idNumber}")
    public String getUser(Model model, Principal principal, @PathVariable int idNumber) {

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("roles", Role.values());
        model.addAttribute("newUser", userService.getUser(idNumber));

        return "usersPages/userProfile";
    }

    // Adding account user
    @PostMapping("/account/{id}/users")
    public RedirectView addAccountUser(@PathVariable Long id, RedirectAttributes redit, HttpServletRequest request) {

        Account account = accountService.getAccount(id);
        List<User> users = userService.getAllUsers();
        List<User> selectedUsers = account.getUsers();

        for (User user : users) {
            if (request.getParameter(user.getIdNumber() + "Input") == null) {
                continue;
            } else {
                selectedUsers.add(user);
            }
        }

        account.setUsers(selectedUsers);
        accountService.updateAccount(account);

        redit.addFlashAttribute("success", "Users successfully added to account.");

        return new RedirectView("/account/" + id, true);

    }

    // Updating user details
    @PostMapping("/user/profile/{idNumber}")
    public RedirectView updateUserProfile(@ModelAttribute User newUser, @PathVariable int idNumber,
            RedirectAttributes redit) {

        User user = userService.getUser(idNumber);

        userService.deleteUserDetails(idNumber);

        user.setFirstName(newUser.getFirstName());
        user.setSecondName(newUser.getSecondName());
        user.setThirdName(newUser.getThirdName());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setIdNumber(newUser.getIdNumber());
        user.setRole(newUser.getRole());

        userService.updateUserDetails(user);
        redit.addFlashAttribute("success", "User details successfully updated.");

        return new RedirectView("/user/profile/" + user.getIdNumber(), true);

    }

    // Changing password
    @PostMapping("/user/{idNumber}/changePassword")
    public RedirectView changePassword(@PathVariable int idNumber, HttpServletRequest request,
            RedirectAttributes redit) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String repeatNewPassword = request.getParameter("repeatNewPassword");
        User user = userService.getUser(idNumber);

        if (encoder.matches(currentPassword, user.getPassword())) {
            if (newPassword.equals(repeatNewPassword)) {
                user.setPassword(encoder.encode(newPassword));
                userService.addUser(user);
                redit.addFlashAttribute("success", "Password successfully changed");
            } else {
                redit.addFlashAttribute("fail", "New Password does not match repeated password");
            }
        } else {
            redit.addFlashAttribute("fail", "Current password is not correct");
        }

        return new RedirectView("/user/profile/" + idNumber, true);
    }

}
