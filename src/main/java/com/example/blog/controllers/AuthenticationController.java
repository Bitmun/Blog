package com.example.blog.controllers;

import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.repo.UserRepository;
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

import java.util.Objects;

@Controller
@SessionAttributes(value = "userDTO")
public class AuthenticationController {
    static final Logger log = LoggerFactory.getLogger(MainController.class);

    private UserRepository userRepository;

    public AuthenticationController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    public String welcome(Model model) {
        model.addAttribute("title", "Welcome");
        return "welcome";
    }
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("userForm", new User());
        return "login";
    }
    @PostMapping("/login")
    public String validateLogin(@ModelAttribute("userForm") User newUser, HttpServletRequest request ) {
        User temp =userRepository.findByEmail(newUser.getEmail());
        log.info("New user: " + temp.toString());
        if (temp == null) {
            log.info("User with such email does not exists");
            return "redirect:/";
        }
        if (Objects.equals(temp.getEmail(), newUser.getEmail()) && Objects.equals(temp.getPassword(), newUser.getPassword())) {
            //modelAndView.addObject("userDTO", temp);
            request.getSession().setAttribute("userDTO", temp);
            log.info("Temp in dto: " + temp.toString());
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
    public String validateRegistration(@ModelAttribute("userForm") User newUser, HttpServletRequest request ) {
        User temp = userRepository.findByEmail(newUser.getEmail());
        if (temp != null) {
            log.info("Email is already in use");
            return "redirect:/";
        }
        newUser.setRole(new Role(1L, "ROLE_USER"));
        userRepository.save(newUser);
        request.getSession().setAttribute("userDTO", newUser);
        return "redirect:/home";
    }
}
