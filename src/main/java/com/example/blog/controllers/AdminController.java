package com.example.blog.controllers;

import com.example.blog.entity.User;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.SessionAttributes;



@Controller
@SessionAttributes(value = "userDTO")
public class AdminController {
    static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {
        User userDTO = (User) request.getSession().getAttribute("userDTO");
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("title", "Admin panel");
        log.info("User in admin: " + userDTO.toString());
        return "adminPanel";
    }

}