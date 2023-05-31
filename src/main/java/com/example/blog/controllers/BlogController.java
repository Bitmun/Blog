package com.example.blog.controllers;

import com.example.blog.service.PostService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import com.example.blog.entity.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class BlogController {
    static final Logger log = LoggerFactory.getLogger(BlogController.class);

    private PostService postService;

    public BlogController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postService.findAll();
        model.addAttribute("posts", posts);
        StringBuilder sb = new StringBuilder();
        for (Object obj : posts) {
            sb.append(obj.toString()).append(", ");
        }
        log.info(sb.toString());
        return "blog-main";
    }
    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("newPost", new Post());
        return "blog-add";
    }
    @PostMapping("/blog/add")
    public String blogPostAdd(@ModelAttribute("newPost") Post newPost) {
        postService.save(newPost);
        log.info("Blog created successfully");
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long postId, Model model) {
        if (!postService.existsById(postId)) {
            log.info("does not exist!");
            return "redirect:/blog";
        }
        Optional<Post> temp = postService.findById(postId);
        Post post = temp.get();
        log.info("Temp to string: " + temp.toString());
        model.addAttribute("post", post);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long postId, Model model) {
        if (!postService.existsById(postId)) {
            return "redirect:/blog";
        }
        Optional<Post> temp = postService.findById(postId);
        Post post = temp.get();
        model.addAttribute("postToChange", post);
        model.addAttribute("postId", postId);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @ModelAttribute("postToChange") Post editedPost) {
        postService.save(editedPost);
        log.info("Blog edited successfully");
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, @ModelAttribute("postToChange") Post editedPost) {
        postService.delete(editedPost);
        log.info("Blog removed successfully");
        return "redirect:/blog";
    }
}
