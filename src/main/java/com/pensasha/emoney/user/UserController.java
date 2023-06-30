package com.pensasha.emoney.user;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.pensasha.emoney.enums.Role;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    // Pages

    // Adding a user Get Request
    @GetMapping("/users/register")
    public String registration(Model model, Principal principal, @ModelAttribute("newUser") User newUser) {

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("roles", Role.values());
        model.addAttribute("newUser", newUser);

        return "registration";

    }

    // Adding a user Post Request
    @PostMapping("/users/register")
    public RedirectView postRegistration(@ModelAttribute("newUser") User newUser, RedirectAttributes redit) {

        if (userService.doesUserExist(newUser.getIdNumber())) {
            redit.addFlashAttribute("newUser", newUser);
            redit.addFlashAttribute("fail", "User with Username:" + newUser.getIdNumber() + " already exists.");

            return new RedirectView("/users/register", true);
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            userService.addUser(newUser);
            redit.addFlashAttribute("success", "User was successfully added.");

            return new RedirectView("/users", true);
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
            userService.deleteUserDetails(idNumber);
            redit.addFlashAttribute("success", "User successfully deleted");
        } else {
            redit.addFlashAttribute("fail", "User with id:" + idNumber + " does not exist.");
        }
        return new RedirectView("/users", true);
    }

    // Getting a single user
    @GetMapping("/user/profile/{idNumber}")
    public String getUser(Model model, Principal principal, @PathVariable int idNumber) {

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("roles", Role.values());
        model.addAttribute("newUser", userService.getUser(idNumber));

        return "usersPages/userProfile";
    }

    //Adding account user
    @PostMapping("/account/{id}/users")
    public RedirectView addAccountUser(@PathVariable Long id, RedirectAttributes redit, HttpServletRequest request){

        
        Account account = accountService.getAccount(id);
        List<User> users = userService.getAllUsers();
        List<User> selectedUsers = new ArrayList<>();


        for(User user : users){
            if(request.getParameter(user.getIdNumber() + "Input").isEmpty()){
                continue;
            }else{
                selectedUsers.add(user);
            }
        }

        account.setUsers(selectedUsers);

        accountService.updateAccount(account);

        return new RedirectView("/account/" + id, true);
 
    }

    // Updating user details
    @PostMapping("/user/profile/{idNumber}")
    public RedirectView updateUserProfile(@ModelAttribute("newUser") User newUser, @PathVariable int idNumber,
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

        if (encoder.matches(currentPassword,user.getPassword())) {
            if (newPassword.equals(repeatNewPassword)) {
                user.setPassword(encoder.encode(newPassword));
                userService.addUser(user);
                redit.addFlashAttribute("success", "Password successfully changed");
            }else{
                redit.addFlashAttribute("fail", "New Password does not match repeated password");
            }
        } else {
            redit.addFlashAttribute("fail", "Current password is not correct");
        }

        return new RedirectView("/user/profile/" + idNumber, true);
    }

}
