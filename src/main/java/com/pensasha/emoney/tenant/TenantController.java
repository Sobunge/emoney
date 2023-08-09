package com.pensasha.emoney.tenant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TenantController {

    @Autowired
    private TenantService tenantService;

    // Adding a tenant Getter
    @GetMapping("/tenants/register")
    public String addingTenantGet() {
        return "/tenantPages/tenantRegistration";
    }

    // Adding a tenant Post
    @PostMapping("/tenants/register")
    public String addingTenantPost() {
        return "redirect:/tenantPages/tenants";
    }

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
    public String tenants() {
        return "/tenantPages/tenants";
    }

    // Deleting a tenant
    @GetMapping("/tenants/{idNumber}")
    public String deleteTenant() {
        return "redirect:/tenantPages/tenants";
    }

}
