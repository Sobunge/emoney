package com.pensasha.emoney.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
}
