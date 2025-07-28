package com.gooderreads.gooder_reads.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author_id")
    private Long author_id;

    @OneToMany(mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    public Book(String title, Long author_id) {
        this.title = title;
        this.author_id = author_id;
    }

    @Override
    public String toString() {
        return title + " by " + author_id;
    }
    
}
