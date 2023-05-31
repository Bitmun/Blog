package com.example.blog.service;

import com.example.blog.entity.Post;
import com.example.blog.repo.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void delete(Post editedPost) {
        postRepository.delete(editedPost);
    }

    public Optional<Post> findById(long postId) {
        return postRepository.findById(postId);
    }

    public boolean existsById(long postId) {
        if (postRepository.existsById(postId)) {
            return true;
        }
        return false;
    }
}
