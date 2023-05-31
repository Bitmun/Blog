package com.example.blog.controllers;

import com.example.blog.entity.Role;
import com.example.blog.entity.User;

import com.example.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@SessionAttributes(value = "userDTO")
public class MainController {
    static final Logger log = LoggerFactory.getLogger(MainController.class);

    private UserService userService;

    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request ) {
        User userDTO = (User) request.getSession().getAttribute("userDTO");
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("title", "Home");
        return "home";
    }
    @PostMapping("/home")
    public String becameAdmin(Model model, HttpServletRequest request) {
        User userDTO = (User) request.getSession().getAttribute("userDTO");
        userService.becameAdmin(userDTO);
        model.addAttribute("userDTO", userDTO);
        return "redirect:/home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us");
        return "about";
    }

}