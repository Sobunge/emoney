package com.pensasha.emoney.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Pages

    // Adding a user Get Request
    @GetMapping("/users/register")
    public String registration() {

        return "registration";

    }

    // Adding a user Post Request
    @PostMapping("/users/register")
    public String postRegistration() {

        return "users";
    }

    // Getting all users
    @GetMapping("/users")
    public String returnAllUsers() {
        return "users";
    }

    // Deleting a user
    @GetMapping("/users/{idNumber}")
    public String deleteUser() {
        return "users";
    }

    // Getting a single user
    @GetMapping("/user/profile/{idNumber}")
    public String getUser() {
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
