package com.pensasha.emoney.config;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.pensasha.emoney.account.Account;
import com.pensasha.emoney.account.AccountService;
import com.pensasha.emoney.enums.Role;
import com.pensasha.emoney.user.User;
import com.pensasha.emoney.user.UserService;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {

    private UserService userService;
    private AccountService accountService;

    public CommandLineAppStartupRunner(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    public void run(String... args) throws Exception {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = new User(32906735, "Samuel", "Odhiambo", "Obunge", "sobunge", 0707335375, encoder.encode("samuel1995"),
                 Arrays.asList(Role.ADMIN));
        userService.addUser(user);

        if (!accountService.doesAccountNameExist("Chama")) {
            Account account = new Account("Chama", "Chama account");
            accountService.addAccount(account);
        }

    }
}
