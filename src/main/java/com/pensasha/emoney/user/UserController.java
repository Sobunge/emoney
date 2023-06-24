package com.pensasha.emoney.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @Autowired
    private UserService userService;

    //Pages

    //Adding a user Get Request
    @GetMapping("/user")
    public String registration(){

        return "registration";

    }
    
    //Adding a user Post Request

    //Updating user details

    //Getting all users

    //Deleting a user

    //Getting a single user

    //Changing username

    //Changing password

}
