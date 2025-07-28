package com.gooderreads.gooder_reads.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "display_name")
    private String displayName;

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();

    public User(String email, String displayName) {
        this.email = email;
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Name: " + displayName + " Email: " + email;
    }
}
