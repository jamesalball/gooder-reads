package com.gooderreads.gooder_reads.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gooderreads.gooder_reads.dto.ReviewDTO;
import com.gooderreads.gooder_reads.entity.Review;
import com.gooderreads.gooder_reads.exception.ResourceNotFoundException;
import com.gooderreads.gooder_reads.mapper.ReviewMapper;
import com.gooderreads.gooder_reads.repository.ReviewRepository;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ReviewMapper reviewMapper;

    @Transactional
    public List<ReviewDTO> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();
        return reviews.stream()
            .map(review -> reviewMapper.convertToDTO(review))
            .collect(Collectors.toList());
    }

    @Transactional
    public ReviewDTO getReviewById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        return reviewMapper.convertToDTO(review);
    }

    @Transactional
    public ReviewDTO createReview(ReviewDTO newReview) {

        Review savedReview = reviewMapper.convertToEntity(newReview);
        savedReview = reviewRepository.save(savedReview);

        ReviewDTO savedReviewDTO = reviewMapper.convertToDTO(savedReview);

        return savedReviewDTO;

    }

    @Transactional
    public ReviewDTO updateReview(Long id, ReviewDTO updatedReview) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        review.setRating(updatedReview.getRating());
        review.setReviewText(updatedReview.getReviewText());

        Review updatedSavedReview = reviewRepository.save(review);
        ReviewDTO updatedReviewDTO = reviewMapper.convertToDTO(updatedSavedReview);

        return updatedReviewDTO;

    }

    @Transactional
    public void deleteReview(Long id) {

        Review review = reviewRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review not found"));

        reviewRepository.delete(review);
        
    }

}
