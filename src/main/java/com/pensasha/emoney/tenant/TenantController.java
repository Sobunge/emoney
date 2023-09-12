package com.pensasha.emoney.tenant;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pensasha.emoney.enums.Role;
import com.pensasha.emoney.user.User;
import com.pensasha.emoney.user.UserService;


@Controller
public class TenantController {

    @Autowired
    private TenantService tenantService;
    @Autowired
    private UserService userService;

    // Saving a tenant
    @GetMapping("/tenant/register")
    public String saveTenant(Tenant newUser, Model model, Principal principal) {

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.TENANT);
        newUser.setRoles(roles);

        model.addAttribute("activeUser",
                userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("roles", roles);
        model.addAttribute("newUser", newUser);

        return "usersPages/registration";
    }
    
    // Geting a tenant
    @GetMapping("/tenant/{idNumber}/profile")
    public String tenantProfileGet(Model model, Principal principal, @PathVariable int idNumber) {

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("roles", Role.values());
        model.addAttribute("newUser", tenantService.getTenant(idNumber));

        return "/tenantPages/tenantProfile";
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
