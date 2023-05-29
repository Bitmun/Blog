package com.example.blog.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import com.example.blog.entity.Post;
import com.example.blog.repo.PostRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping
@Controller
public class BlogController {
    static final Logger log = LoggerFactory.getLogger(BlogController.class);

    private PostRepository postRepository;

    public BlogController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/blog")
    public String blogMain(Model model) {
        Iterable<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "blog-main";
    }
    @GetMapping("/blog/add")
    public String blogAdd(Model model) {
        model.addAttribute("newPost", new Post());
        return "blog-add";
    }
    @PostMapping("/blog/add")
    public String blogPostAdd(@ModelAttribute("newPost") Post newPost) {
        postRepository.save(newPost);
        log.info("Blog created successfully");
        return "redirect:/blog";
    }

    @GetMapping("/blog/{id}")
    public String blogDetails(@PathVariable(value = "id") long postId, Model model) {
        if (!postRepository.existsById(postId)) {
            return "redirect:/blog";
        }

        Optional<Post> temp = postRepository.findById(postId);
        Post post = temp.get();
        model.addAttribute("post", post);
        return "blog-details";
    }

    @GetMapping("/blog/{id}/edit")
    public String blogEdit(@PathVariable(value = "id") long postId, Model model) {
        if (!postRepository.existsById(postId)) {
            return "redirect:/blog";
        }
        Optional<Post> temp = postRepository.findById(postId);
        Post post = temp.get();
        model.addAttribute("postToChange", post);
        model.addAttribute("postId", postId);
        return "blog-edit";
    }

    @PostMapping("/blog/{id}/edit")
    public String blogPostUpdate(@PathVariable(value = "id") long id, @ModelAttribute("postToChange") Post editedPost) {
        postRepository.save(editedPost);
        log.info("Blog edited successfully");
        return "redirect:/blog";
    }

    @PostMapping("/blog/{id}/remove")
    public String blogPostDelete(@PathVariable(value = "id") long id, @ModelAttribute("postToChange") Post editedPost) {
        postRepository.delete(editedPost);
        log.info("Blog removed successfully");
        return "redirect:/blog";
    }
}
