package com.gooderreads.gooder_reads.dto;

import com.gooderreads.gooder_reads.entity.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private Long bookId;
    private Long userId;
    private double rating;
    private String reviewText;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.bookId = review.getBook().getId();
        this.userId = review.getUser().getId();
        this.rating = review.getRating();
        this.reviewText = review.getReviewText();
    }
}
