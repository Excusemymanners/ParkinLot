package org.example.parkinglot.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "usergroups") // Numele tabelului cerut: usergroups
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "userGroup", nullable = false) // Numele câmpului cerut: userGroup
    private String userGroup;

    // Constructori
    public UserGroup() {
    }

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
}
