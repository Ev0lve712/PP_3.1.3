package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("hasEditPermission")
    public boolean hasEditPermission(@AuthenticationPrincipal UserDetails user) {
        return user != null && user.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping("/")
    public String login() {
        return "login";
    }

    @GetMapping("/user")
    public String userProfile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("userDetail", userDetails);
        model.addAttribute("user", userService.findByUsername(userDetails.getUsername()));
        return "user";
    }

    @GetMapping("/admin")
    public String adminPanel(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        model.addAttribute("user", userDetails);
        return "admin";
    }
}