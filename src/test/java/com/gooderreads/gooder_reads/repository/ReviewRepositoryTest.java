package com.gooderreads.gooder_reads.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import com.gooderreads.gooder_reads.entity.Book;
import com.gooderreads.gooder_reads.entity.Review;
import com.gooderreads.gooder_reads.entity.User;

@DataJpaTest
public class ReviewRepositoryTest {
    
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    TestEntityManager entityManager;

    private Long authorId = 1L;
    private Book book;
    private Book bookB;
    private User user;

    @BeforeEach
    void setUp() {
        book = entityManager.persist(new Book("The Stand", authorId));
        bookB = entityManager.persist(new Book("IT", authorId));
        user = entityManager.persist(new User("test@testing.com", "Test User"));
    }

    @Test
    void testSaveNewReview() {
        Review newReview = new Review();
        Review savedReview = reviewRepository.save(newReview);
        assertThat(entityManager.find(Review.class, savedReview.getId())).isEqualTo(newReview);
    }

    @Test
    void testUpdateExistingReview() {
        Review review = new Review(book, user, 5.0, "This book rocks!");
        entityManager.persist(review);
        double newRating = 4.0;
        review.setRating(newRating);
        reviewRepository.save(review);
        assertThat(entityManager.find(Review.class, review.getId()).getRating()).isEqualTo(newRating);
    }

    @Test
    void testFindAllReviews() {
        Review reviewA = new Review(book, user, 5.0, "This book rocks!");
        Review reviewB = new Review(bookB, user, 4.5, "This book is pretty good!");
        entityManager.persist(reviewA);
        entityManager.persist(reviewB);

        List<Review> reviewList = reviewRepository.findAll();

        assertThat(entityManager.find(Review.class, reviewA.getId())).isEqualTo(reviewList.get(0));

    }

    @Test
    void testFindReviewById() {
        Review review = new Review(book, user, 5.0, "This book rocks!");
        entityManager.persist(review);
        Optional<Review> actualReview = reviewRepository.findById(review.getId());
        assertThat(actualReview).contains(review);
    }

    @Test
    void testDeleteReview() {
        Review review = new Review(book, user, 5.0, "This book rocks!");
        entityManager.persist(review);
        reviewRepository.delete(review);
        assertThat(entityManager.find(Review.class, review.getId())).isNull();
    }
}
