package ru.kata.spring.boot_security.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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

    @GetMapping("/admin/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "redirect:/admin";
    }

    @PostMapping("/admin/add")
    public String addUser(@ModelAttribute User user) {
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        User user = userService.getById(id);
        model.addAttribute("user", user);
        return "edit-user";
    }

    @PostMapping("/admin/edit")
    public String updateUser(@ModelAttribute User user) {
        userService.edit(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}