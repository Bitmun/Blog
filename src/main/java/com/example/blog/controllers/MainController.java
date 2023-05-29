package com.example.blog.controllers;


import com.example.blog.entity.Post;
import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.repo.PostRepository;
import com.example.blog.repo.UserRepository;
import com.example.blog.service.UserService;
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

    private ModelAndView modelAndView = new ModelAndView();


    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("title", "Welcome");
        return "welcome";
    }

    @GetMapping("/home")
    public String home(Model model) {
        if (modelAndView.getModel().get("userDTO") == null) {
            log.info("No userDTO");
            return "redirect:/";
        }
        log.info("UserDTO in Home: " + modelAndView.getModel().get("userDTO").toString());
        model.addAttribute("userDTO", (User) modelAndView.getModel().get("userDTO"));
        model.addAttribute("title", "Home");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "About us");
        return "about";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userForm", new User());
        return "login";
    }

    @PostMapping("/login")
    public String validateLogin(@ModelAttribute("userForm") User newUser) {
        User temp =userRepository.findByEmail(newUser.getEmail());
        log.info("New user: " + temp.toString());
        if (temp == null) {
            log.info("User with such email does not exists");
            return "redirect:/";
        }
        if (Objects.equals(temp.getEmail(), newUser.getEmail()) && Objects.equals(temp.getPassword(), newUser.getPassword())) {
            modelAndView.addObject("userDTO", temp);
            return "redirect:/home";
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String validateRegistration(@ModelAttribute("userForm") User newUser) {
        User temp = userRepository.findByEmail(newUser.getEmail());
        if (temp != null) {
            log.info("Email is already in use");
            return "redirect:/";
        }
        newUser.setRole(new Role(1L, "ROLE_USER"));
        userRepository.save(newUser);
        modelAndView.addObject("userDTO", newUser);
        return "redirect:/home";
    }

}