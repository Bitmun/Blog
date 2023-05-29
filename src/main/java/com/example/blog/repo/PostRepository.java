package com.example.blog.repo;

import com.example.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Crud contains all necessary methods
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
