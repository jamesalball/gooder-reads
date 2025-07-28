package com.gooderreads.gooder_reads.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Column;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "Reviews")
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rating")
    private double rating;

    @Column(name = "review_text")
    private String reviewText;

    public Review(Book book, User user, double rating, String reviewText) {
        this.book = book;
        this.user = user;
        this.rating = rating;
        this.reviewText = reviewText;
    }

    @Override
    public String toString() {
        return "User " + user.getDisplayName() + " gave Book " + book.getTitle() + " a rating of " + rating;
    }
    
}
