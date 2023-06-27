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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pensasha.emoney.enums.Role;

import jakarta.servlet.http.HttpServletRequest;

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
    public String postRegistration(@ModelAttribute("newUser") User newUser, RedirectAttributes redit) {

        if (userService.doesUserExist(newUser.getIdNumber())) {
            redit.addAttribute("fail", "User with username:" + newUser.getIdNumber() + " already exists.");

            return "registration";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            userService.addUser(newUser);
            redit.addAttribute("success", "User was successfully added.");

            return "redirect:/users";
        }

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
    public String deleteUser(@PathVariable int idNumber, RedirectAttributes redit) {
        if (userService.doesUserExist(idNumber)) {
            userService.deleteUserDetails(idNumber);
            redit.addAttribute("success", "User successfully deleted");
        } else {
            redit.addAttribute("fail", "User with id:" + idNumber + " does not exist.");
        }
        return "redirect:/users";
    }

    // Getting a single user
    @GetMapping("/user/profile/{idNumber}")
    public String getUser(Model model, Principal principal, @PathVariable int idNumber) {

        model.addAttribute("activeUser", userService.getUser(Integer.parseInt(principal.getName())));
        model.addAttribute("roles", Role.values());
        model.addAttribute("newUser", userService.getUser(idNumber));

        return "userProfile";
    }

    // Updating user details
    @PostMapping("/user/profile/{idNumber}")
    public String updateUserProfile(@ModelAttribute("newUser") User newUser, @PathVariable int idNumber, 
            RedirectAttributes redit) {

        User user = userService.getUser(idNumber);

        userService.deleteUserDetails(idNumber);

        user.setFirstName(newUser.getFirstName());
        user.setSecondName(newUser.getSecondName());
        user.setThirdName(newUser.getThirdName());
        user.setPhoneNumber(newUser.getPhoneNumber());
        user.setIdNumber(newUser.getIdNumber());
        user.setRole(newUser.getRole());

        userService.updateUserDetails(user);
        redit.addAttribute("success", "User details successfully updated.");

        return "redirect:/user/profile/" + user.getIdNumber();

    }

    // Changing password
    @PostMapping("/user/{idNumber}/changePassword")
    public String changePassword(@PathVariable int idNumber, HttpServletRequest request, RedirectAttributes redit) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String currentPassword = encoder.encode(request.getParameter("currentPassword"));
        String newPassword = request.getParameter("newPassword");
        String repeatNewPassword = request.getParameter("repeatNewPassword");
        User user = userService.getUser(idNumber);

        if (currentPassword == user.getPassword()) {
            if (newPassword == repeatNewPassword) {
                user.setPassword(encoder.encode(newPassword));
                userService.addUser(user);
                redit.addAttribute("success", "Password successfully changed");
            }
        } else {
            redit.addAttribute("fail", "Current password is not correct");
        }

        return "redirect:/user/profile/" + idNumber;
    }

}
