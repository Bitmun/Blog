package com.example.blog.entity;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role {

    @Id
    private Long id;
    private String name;
    @Transient
    @OneToMany(mappedBy = "roles")
    private Set<User> users;
    public Role() {
    }
    @Transient
    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        Role userRole = new Role(1L, "ROLE_USER");
        userRole.setName("ROLE_USER");
        entityManager.persist(userRole);
        Role adminRole = new Role(2L, "ROLE_ADMIN");
        adminRole.setName("ROLE_ADMIN");
        entityManager.persist(adminRole);
    }
    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }
}