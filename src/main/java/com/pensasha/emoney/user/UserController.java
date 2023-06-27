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
import org.springframework.web.bind.annotation.ResponseBody;
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
    public String postRegistration(@ModelAttribute("newUser") User newUser, Model model) {

        if (userService.doesUserExist(newUser.getIdNumber())) {
            return "registration";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            newUser.setPassword(encoder.encode(newUser.getPassword()));
            userService.addUser(newUser);

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
    public String deleteUser(@PathVariable int idNumber) {
        if (userService.doesUserExist(idNumber)) {
            userService.deleteUserDetails(idNumber);
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
    public String updateUserProfile(@ModelAttribute("newUser") User newUser, @PathVariable int idNumber, Model model,
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

        return "redirect:/user/profile/" + user.getIdNumber();

    }

    // Changing password
    @PostMapping("/user/{idNumber}/changePassword")
    @ResponseBody
    public String changePassword(@PathVariable int idNumber, HttpServletRequest request){
        return request.getParameter("currentPassword");
        //return "redirect:/user/profile/" + idNumber;
    }

}
