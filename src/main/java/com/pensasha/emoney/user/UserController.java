package com.pensasha.emoney.user;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pensasha.emoney.enums.Role;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

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
    public String postRegistration(@ModelAttribute("newUser") User newUser, Model model) {

        if(userService.doesUserExist(newUser.getIdNumber())){
            return "registration";
        }else{
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            userService.addUser(newUser);
        }

        return "users";
    }

    // Getting all users
    @GetMapping("/users")
    public String returnAllUsers(Principal principal, Model model) {
        
        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("users", userService.getAllUsers());

        return "users";
    }

    // Deleting a user
    @GetMapping("/users/{idNumber}")
    public String deleteUser() {
        return "users";
    }

    // Getting a single user
    @GetMapping("/user/profile/{idNumber}")
    public String getUser(Model model, Principal principal, @PathVariable int idNumber) {

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("roles", Role.values());
        model.addAttribute("newUser", new User());

        return "user";
    }

    // Updating user details
    @PostMapping("/user/profile/{idNumber}")
    public String updateUserProfile() {
        return "user";
    }

    // Changing username

    // Changing password

}
