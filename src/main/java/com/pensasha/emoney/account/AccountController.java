package com.pensasha.emoney.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {
    
    @Autowired
    private AccountService accountService;

    
}
