package com.pensasha.emoney.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pensasha.emoney.user.User;
import com.pensasha.emoney.user.UserService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    
    @Autowired
    private UserService userService;

    public void run(String... args) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
        User user = new User(32906735,"Samuel", "Odhiambo", "Obunge", 0707335375, encoder.encode("samuel1995"));

        userService.addUser(user);

    }
}
