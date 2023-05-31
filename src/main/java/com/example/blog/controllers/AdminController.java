package com.example.blog.controllers;

import com.example.blog.entity.Post;
import com.example.blog.entity.User;

import com.example.blog.repo.PostRepository;
import com.example.blog.service.UserService;
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

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String admin(Model model, HttpServletRequest request) {
        User userDTO = (User) request.getSession().getAttribute("userDTO");
        model.addAttribute("userDTO", userDTO);
        model.addAttribute("title", "Admin panel");
        log.info("User in admin: " + userDTO.toString());
        return "adminPanel";
    }

    @GetMapping("/admin/allUsers")
    public String allUsers(Model model, HttpServletRequest request) {
        Iterable<User> users = userService.findAll();
        model.addAttribute("users", users);
        return "allUsers";
    }

//    @GetMapping("/blog")
//    public String blogMain(Model model) {
//        Iterable<Post> posts = postRepository.findAll();
//        model.addAttribute("posts", posts);
//        StringBuilder sb = new StringBuilder();
//        for (Object obj : posts) {
//            sb.append(obj.toString()).append(", ");
//        }
//        log.info(sb.toString());
//        return "blog-main";
//    }

}