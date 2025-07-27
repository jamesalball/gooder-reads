package com.gooderreads.gooder_reads.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "pass")
    private String pass;

    @Column(name = "display_name")
    private String displayName;

    protected User() {}

    public User(String email, String pass, String displayName) {
        this.email = email;
        this.pass = pass;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Name: " + displayName + " Email: " + email;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getDisplayName() {
        return displayName;
    }
    
}
