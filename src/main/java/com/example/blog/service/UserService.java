package com.example.blog.service;

import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.repo.RoleRepository;
import com.example.blog.repo.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager em;

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public void becameAdmin(User userDTO) {
        userDTO.setRole(new Role(2L, "ROLE_ADMIN"));
    }

    public void becameUser(User userDTO) {
        userDTO.setRole(new Role(1L, "ROLE_USER"));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return user;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }
}
