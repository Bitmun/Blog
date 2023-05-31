package com.example.blog.entity;

import com.example.blog.controllers.MainController;
import jakarta.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    static final Logger log = LoggerFactory.getLogger(MainController.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private String email;
    @Transient
    private String passwordConfirm;


    @ManyToOne(fetch = FetchType.EAGER)
    private Role role;

    public User() {
    }

    public User(String username, String password, String passwordConfirm, String email) {
        this.username = username;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                '}';
    }

    public boolean hasRole(String roleName) {
        return Objects.equals(role.getName(), roleName);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Role getRoles() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
