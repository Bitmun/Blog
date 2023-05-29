package com.example.blog.controllers;


import com.example.blog.entity.Post;
import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.repo.PostRepository;
import com.example.blog.repo.UserRepository;
import com.example.blog.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.Objects;


@Controller
@SessionAttributes(value = "userDTO")
public class MainController {
    static final Logger log = LoggerFactory.getLogger(MainController.class);

    private UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/home")
    public String home(Model model, HttpServletRequest request ) {
        User userDTO = (User) request.getSession().getAttribute("userDTO");
        if (userDTO == null) {
            log.info("No userDTO");
            return "redirect:/";
        }
        log.info("UserDTO in Home: " + userDTO.toString());
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us");
        return "about";
    }

}