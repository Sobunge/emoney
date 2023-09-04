package com.pensasha.emoney.tenant;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pensasha.emoney.enums.Role;
import com.pensasha.emoney.user.UserService;

import jakarta.validation.Valid;

@Controller
public class TenantController {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private UserService userService;

    // Geting a tenant
    @GetMapping("/tenant/{idNumber}/profile")
    public String tenantProfileGet() {
        return "tenantPages/tenantProfile";
    }

    // Updating a tenant Post
    @PostMapping("/tenant/{idNumber}/profile")
    public String tenantProfilePost() {
        return "redirect:/tenantPages/tenantProfile";
    }

    // Getting all tenants
    @GetMapping("/tenants")
    public String tenants(Principal principal, Model model) {

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("tenants", tenantService.getAllTenants());

        return "/tenantPages/tenants";
    }

    // Deleting a tenant
    @GetMapping("/tenants/{idNumber}")
    public String deleteTenant() {
        return "redirect:/tenantPages/tenants";
    }

}
